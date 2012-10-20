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
        return 1;
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

            this.iterAttached(cardDiv, cardId, props, rootRecognizeFunc, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    var attBox = that.getCardBox(attCardDiv, attCardId, attProps);
                    if (attachLeft < 0)
                        minLeft += attachLeft * cardWidth;
                    else if (attachLeft > 0)
                        maxRight += attachLeft * cardWidth;

                    if (attachTop < 0)
                        minTop += attachTop * cardHeight;
                    else if (attachTop > 0)
                        maxBottom = + attachTop * cardHeight;
                });
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
        log(cardId+": "+boxWidth+","+boxHeight);
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
                    log("setup attached: "+attCardId);
                    index++;
                    var attCardBox = that.getCardBox(attCardDiv, attCardId, attProps);
                    that.layoutOneCard(attCardDiv, attCardId, attProps, layout, zIndex, 
                        cardLeft + index*cardWidth * attachLeft, cardTop + index*cardHeight * attachTop,
                        boxWidth*(attCardBox.right-attCardBox.left)/(cardBox.right-cardBox.left), boxHeight*(attCardBox.bottom - attCardBox.top)/(cardBox.bottom-cardBox.top), scale);
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