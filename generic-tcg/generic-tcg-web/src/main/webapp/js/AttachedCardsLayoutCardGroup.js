var AttachedCardsLayoutCardGroup = RowCardLayoutCardGroup.extend({
    attachedGroupsLeft: null,
    attachedGroupsTop: null,
    attachedGroupsFinderFunc: null,

    rootRecognizeFunc: null,

    /**
     * Root recognize function is call to find all root cards, parameters
     * cardDiv, cardId, props
     *
     * @param cardContainerDiv
     * @param cardContainFunc
     * @param rootRecognizeFunc
     */
    init: function (cardContainerDiv, cardContainFunc, rootRecognizeFunc) {
        this._super(cardContainerDiv, cardContainFunc);
        this.attachedGroupsLeft = new Array();
        this.attachedGroupsTop = new Array();
        this.attachedGroupsFinderFunc = new Array();

        this.rootRecognizeFunc = rootRecognizeFunc;
    },

    addAttachedGroup: function(left, top, finderFunc) {
        this.attachedGroupsLeft.push(left);
        this.attachedGroupsTop.push(top);
        this.attachedGroupsFinderFunc.push(finderFunc);
    },

    iterCardGroupBoxes: function(func) {
        log("AttachedCardsLayoutCardGroup::iterCardGroupBoxes");
        var that = this;

        this.iterCards(
                function(cardDiv, cardId, props) {
                    if (that.rootRecognizeFunc(cardDiv, cardId, props))
                        func(cardDiv, cardId, props);
                });
    },

    iterAttached: function(cardDiv, cardId, props, finderFunc, func) {
        log("AttachedCardsLayoutCardGroup::iterAttached "+cardId);
        var that = this;
        this.iterCards(
            function(cardDivAtt, cardIdAtt, propsAtt) {
                if (that.rootRecognizeFunc(cardDivAtt, cardIdAtt, propsAtt))
                    return;
                
                if (finderFunc(cardDiv, cardId, props, cardDivAtt, cardIdAtt, propsAtt))
                    func(cardDivAtt, cardIdAtt, propsAtt);
            });
    },

    getCardHeightScale: function(cardGroupBoxSize) {
        return Math.min(1, 1 / (cardGroupBoxSize.bottom - cardGroupBoxSize.top));
    },

    getCardGroupBoxSize: function(widthToHeightFunc, cardDiv, cardId, props) {
        log("AttachedCardsLayoutCardGroup::getCardGroupBoxSize "+cardId);
        var that = this;

        var minLeft = 0;
        var minTop = 0;
        var maxRight = Math.min(1, widthToHeightFunc(cardId, props));
        var maxBottom = Math.min(1, 1 / widthToHeightFunc(cardId, props));
        var cardWidth = maxRight;
        var cardHeight = maxBottom;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            var attIndex = 0;
            this.iterAttached(cardDiv, cardId, props, attachFunc,
                function(attCardDiv, attCardId, attProps, attWidthToHeightRatioFunc) {
                    attIndex++;
                    var attBox = that.getCardBox(widthToHeightFunc, attCardDiv, attCardId, attProps);
                    var attWidth = attBox.right - attBox.left;
                    var attHeight = attBox.bottom - attBox.top;
                    if (attachLeft < 0) {
                        maxRight = Math.max(maxRight, attachLeft * attIndex + attWidth);
                    } else if (attachLeft > 0) {
                        minLeft = Math.min(minLeft, cardWidth + attachLeft*attIndex - attWidth);
                    }

                    if (attachTop < 0) {
                        maxBottom = Math.max(maxBottom, attachTop * attIndex + attHeight);
                    } else if (attachTop > 0) {
                        minTop = Math.min(minTop, cardHeight + attachTop*attIndex - attHeight);
                    }
                });

            if (attachLeft < 0)
                minLeft = Math.min(minLeft, attachLeft * attIndex);
            else
                maxRight = Math.max(maxRight, cardWidth + attachLeft * attIndex);

            if (attachTop < 0)
                minTop = Math.min(minTop, attachTop * attIndex);
            else
                maxBottom = Math.max(maxBottom, cardHeight + attachTop * attIndex);
        }
        var result = {};
        result.left = minLeft;
        result.top = minTop;
        result.right = maxRight;
        result.bottom = maxBottom;
        return result;
    },

    layoutCardGroup: function(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, zIndex, cardBox, boxLeft, boxTop, boxWidth, boxHeight) {
        log("AttachedCardsLayoutCardGroup::layoutCardGroup "+cardId);

        var that = this;
        var pixelSize = boxWidth / (cardBox.right - cardBox.left);
        var cardGroupHeight = pixelSize * (cardBox.bottom - cardBox.top);
        var cardGroupWidth = pixelSize * (cardBox.right - cardBox.left);

        var cardRatio = widthToHeightFunc(cardId, props);
        var cardWidth = pixelSize * Math.min(1, cardRatio);
        var cardHeight = cardWidth / cardRatio;
        var cardLeft = boxLeft - cardBox.left * pixelSize;
        var cardTop = boxTop - cardBox.top * pixelSize + (boxHeight - cardGroupHeight) / 2;
        layoutFunc(cardDiv, cardId, props, zIndex, cardLeft, cardTop, cardWidth, cardHeight);

        zIndex--;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            var index = 0;
            this.iterAttached(cardDiv, cardId, props, attachFunc,
                function(attCardDiv, attCardId, attProps, attWidthToHeightRatioFunc) {
                    index++;
                    var attCardBox = that.getCardBox(widthToHeightFunc, attCardDiv, attCardId, attProps);
                    var attWidth = pixelSize * (attCardBox.right - attCardBox.left);
                    var attHeight = pixelSize * (attCardBox.bottom - attCardBox.top);

                    var attLeft;
                    if (attachLeft <= 0)
                        attLeft = cardLeft + index * pixelSize * attachLeft;
                    else
                        attLeft = cardLeft + cardWidth - attWidth + index * pixelSize * attachLeft;
                    var attTop;
                    if (attachTop <= 0)
                        attTop = cardTop + index * pixelSize * attachTop;
                    else
                        attTop = cardTop + cardHeight - attHeight + index * pixelSize * attachTop;
                    layoutFunc(attCardDiv, attCardId, attProps, zIndex,
                        attLeft, attTop,
                        attWidth, attHeight);
                    zIndex--;
                });
        }
    },

    layoutCardGroupBox: function(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, boxLeft, boxTop, boxWidth, boxHeight, cardBox) {
        var zIndex = this.zIndexBase;
        this.layoutCardGroup(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, zIndex, cardBox, boxLeft, boxTop, boxWidth, boxHeight);
    }
});