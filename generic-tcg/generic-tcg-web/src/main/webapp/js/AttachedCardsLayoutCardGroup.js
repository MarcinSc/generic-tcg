var AttachedCardsLayoutCardGroup = RowCardLayoutCardGroup.extend({
    attachedGroupsLeft: null,
    attachedGroupsTop: null,
    attachedGroupsFinderFunc: null,
    attachedGroupsRootRecognizeFunc: null,

    init: function (cardContainerDiv, cardContainFunc) {
        this._super(cardContainerDiv, cardContainFunc);
        this.attachedGroupsLeft = new Array();
        this.attachedGroupsTop = new Array();
        this.attachedGroupsFinderFunc = new Array();
        this.attachedGroupsRootRecognizeFunc = new Array();
    },

    addAttachedGroup: function(left, top, rootRecognizeFunc, finderFunc) {
        this.attachedGroupsLeft.push(left);
        this.attachedGroupsTop.push(top);
        this.attachedGroupsRootRecognizeFunc.push(rootRecognizeFunc);
        this.attachedGroupsFinderFunc.push(finderFunc);
    },

    iterAttached: function(cardDiv, cardId, props, rootRecognizeFunc, finderFunc, func) {
        if (!rootRecognizeFunc(cardDiv, cardId, props))
            return;

        $(".card", this.cardContainerDiv).each(
            function() {
                var cardDivAtt = $(this);
                var cardIdAtt = cardDivAtt.data("id");
                var propsAtt = cardDivAtt.data("props");
                var layout = cardDivAtt.data("layout");
                var widthToHeightScaleFunc = cardDivAtt.data("widthToHeight");
                if (finderFunc(cardDiv, cardId, props, cardDivAtt, cardIdAtt, propsAtt))
                    func(cardDivAtt, cardIdAtt, propsAtt, layout, widthToHeightScaleFunc);
            });
    },

    getCardHeightScale: function(cardDiv, cardId, props) {
        var cardBox = this.getCardBox(cardDiv, cardId, props);
        return Math.min(1, 1 / (cardBox.bottom - cardBox.top));
    },

    getCardBox: function(cardDiv, cardId, props) {
        var that = this;

        var minLeft = 0;
        var minTop = 0;
        var maxRight = Math.min(1, cardDiv.data("widthToHeight")(cardId, props));
        var maxBottom = Math.min(1, 1/cardDiv.data("widthToHeight")(cardId, props));
        var cardWidth = maxRight;
        var cardHeight = maxBottom;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var rootRecognizeFunc = this.attachedGroupsRootRecognizeFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            var attIndex = 0;
            this.iterAttached(cardDiv, cardId, props, rootRecognizeFunc, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    var attBox = that.getCardBox(attCardDiv, attCardId, attProps);
                    var attWidth = attBox.right - attBox.left;
                    var attHeight = attBox.bottom - attBox.top;
                    if (attachLeft < 0) {
                        maxRight = Math.max(maxRight, attachLeft * cardWidth + attWidth);
                    } else if (attachLeft > 0) {
                        minLeft = Math.min(minLeft, cardWidth + attachLeft * cardWidth - attWidth);
                    }

                    if (attachTop < 0) {
                        maxBottom = Math.max(maxBottom, attachTop * cardHeight + attHeight);
                    } else if (attachTop > 0) {
                        minTop = Math.min(minTop, cardHeight + attachTop * cardHeight - attHeight);
                    }
                    attIndex++;
                });

            if (attachLeft < 0)
                minLeft = Math.min(minLeft, attachLeft * cardWidth * attIndex);
            else
                maxRight = Math.max(maxRight, cardWidth + attachLeft * cardWidth * attIndex);

            if (attachTop < 0)
                minTop = Math.min(minTop, attachTop * cardHeight * attIndex);
            else
                maxBottom = Math.max(maxBottom, cardHeight + attachTop * cardHeight * attIndex);
        }
        var result = {};
        result.left = minLeft;
        result.top = minTop;
        result.right = maxRight;
        result.bottom = maxBottom;
        return result;
    },

    getCardBoxRatio: function(cardDiv, cardId, props) {
        var cardBox = this.getCardBox(cardDiv, cardId, props);
        var result = {};
        result.x = cardBox.right-cardBox.left;
        result.y = cardBox.bottom-cardBox.top;
        return result;
    },

    layoutCardGroup: function(cardDiv, cardId, props, layout, zIndex, cardBox, boxLeft, boxTop, boxWidth, boxHeight, scale) {
        var that = this;
        var cardRatio = cardDiv.data("widthToHeight")(cardId, props);
        var cardWidth = boxWidth / ((cardBox.right - cardBox.left) / Math.min(1, cardRatio));
        var cardHeight = cardWidth / cardRatio;
        var cardLeft = boxLeft - cardBox.left * cardWidth / cardRatio;
        var cardTop = boxTop - cardBox.top * cardHeight + (boxHeight - cardHeight) / 2;
        this.layoutOneCard(cardDiv, cardId, props, layout, zIndex, cardLeft, cardTop, cardWidth, cardHeight);

        var maxSize = Math.max(cardWidth, cardHeight);

        zIndex--;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var rootRecognizeFunc = this.attachedGroupsRootRecognizeFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            var index = 0;
            this.iterAttached(cardDiv, cardId, props, rootRecognizeFunc, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    index++;
                    var attCardBox = that.getCardBox(attCardDiv, attCardId, attProps);
                    var attWidth = boxWidth*(attCardBox.right-attCardBox.left)/(cardBox.right-cardBox.left);
                    var attHeight = boxHeight*(attCardBox.bottom - attCardBox.top)/(cardBox.bottom-cardBox.top) * scale;

                    var attLeft;
                    if (attachLeft <= 0)
                        attLeft = cardLeft + index * cardWidth * attachLeft;
                    else
                        attLeft = cardLeft + cardWidth - attWidth + index * cardWidth * attachLeft;
                    var attTop;
                    if (attachTop <= 0)
                        attTop = cardTop + index * cardHeight * attachTop;
                    else
                        attTop = cardTop + cardHeight - attHeight + index * cardHeight * attachTop;
                    that.layoutOneCard(attCardDiv, attCardId, attProps, layout, zIndex,
                        attLeft, attTop,
                        attWidth, attHeight);
                    zIndex--;
                });
        }
    },

    layoutCardBox: function(cardDiv, cardId, props, layout, boxLeft, boxTop, boxWidth, boxHeight, ratio, scale) {
        var that = this;
        var zIndex = this.zIndexBase;
        var cardBox = this.getCardBox(cardDiv, cardId, props);
        this.layoutCardGroup(cardDiv, cardId, props, layout, zIndex, cardBox, boxLeft, boxTop, boxWidth, boxHeight, scale);
    }
});