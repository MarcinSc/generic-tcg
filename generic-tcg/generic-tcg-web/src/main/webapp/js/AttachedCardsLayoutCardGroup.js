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
        var that = this;

        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    if (that.rootRecognizeFunc(cardDiv, cardId, props))
                        func(cardDiv, cardId, props, layout);
                });
    },

    iterAttached: function(cardDiv, cardId, props, finderFunc, func) {
        log("AttachedCardsLayoutCardGroup::iterAttached");
        var that = this;
        this.iterCards(
            function(cardDivAtt, cardIdAtt, propsAtt, layout) {
                if (that.rootRecognizeFunc(cardDivAtt, cardIdAtt, propsAtt))
                    return;
                
                var widthToHeightScaleFunc = cardDivAtt.data("widthToHeight");
                if (finderFunc(cardDiv, cardId, props, cardDivAtt, cardIdAtt, propsAtt))
                    func(cardDivAtt, cardIdAtt, propsAtt, layout, widthToHeightScaleFunc);
            });
    },

    getCardHeightScale: function(cardGroupBoxSize) {
        return Math.min(1, 1 / (cardGroupBoxSize.bottom - cardGroupBoxSize.top));
    },

    getCardBox: function(cardDiv, cardId, props) {
        var cardRatio = cardDiv.data("widthToHeight")(cardId, props);
        var result = {};
        result.left = 0;
        result.top = 0;
        result.right = Math.min(1, cardRatio);
        result.bottom = Math.min(1, 1 / cardRatio);
        return result;
    },

    getCardGroupBoxSize: function(cardDiv, cardId, props) {
        var that = this;

        var minLeft = 0;
        var minTop = 0;
        var maxRight = Math.min(1, cardDiv.data("widthToHeight")(cardId, props));
        var maxBottom = Math.min(1, 1 / cardDiv.data("widthToHeight")(cardId, props));
        var cardWidth = maxRight;
        var cardHeight = maxBottom;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            var attIndex = 0;
            this.iterAttached(cardDiv, cardId, props, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    attIndex++;
                    var attBox = that.getCardBox(attCardDiv, attCardId, attProps);
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

    layoutCardGroup: function(cardDiv, cardId, props, layout, zIndex, cardBox, boxLeft, boxTop, boxWidth, boxHeight) {
        var that = this;
        var pixelSize = boxWidth / (cardBox.right - cardBox.left);
        var cardGroupHeight = pixelSize * (cardBox.bottom - cardBox.top);
        var cardGroupWidth = pixelSize * (cardBox.right - cardBox.left);

        var cardRatio = cardDiv.data("widthToHeight")(cardId, props);
        var cardWidth = pixelSize * Math.min(1, cardRatio);
        var cardHeight = cardWidth / cardRatio;
        var cardLeft = boxLeft - cardBox.left * pixelSize;
        var cardTop = boxTop - cardBox.top * pixelSize + (boxHeight - cardGroupHeight) / 2;
        this.layoutOneCard(cardDiv, cardId, props, layout, zIndex, cardLeft, cardTop, cardWidth, cardHeight);

        zIndex--;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            var index = 0;
            this.iterAttached(cardDiv, cardId, props, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    index++;
                    var attCardBox = that.getCardBox(attCardDiv, attCardId, attProps);
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
                    that.layoutOneCard(attCardDiv, attCardId, attProps, layout, zIndex,
                        attLeft, attTop,
                        attWidth, attHeight);
                    zIndex--;
                });
        }
    },

    layoutCardGroupBox: function(cardDiv, cardId, props, layout, boxLeft, boxTop, boxWidth, boxHeight, ratio) {
        var that = this;
        var zIndex = this.zIndexBase;
        var cardBox = this.getCardGroupBoxSize(cardDiv, cardId, props);
        this.layoutCardGroup(cardDiv, cardId, props, layout, zIndex, cardBox, boxLeft, boxTop, boxWidth, boxHeight);
    }
});