var AttachedCardsLayoutCardGroup = RowCardLayoutCardGroup.extend({
    attachedGroupsLeft: null,
    attachedGroupsTop: null,
    attachedGroupsFinderFunc: null,

    init: function (cardContainerDiv, cardContainFunc) {
        this._super(cardContainerDiv, cardContainFunc);
        this.attachedGroupsLeft = new Array();
        this.attachedGroupsTop = new Array();
        this.attachedGroupsFinderFunc = new Array();
    },

    addAttachedGroup: function(left, top, finderFunc) {
        this.attachedGroupsLeft.push(left);
        this.attachedGroupsTop.push(top);
        this.attachedGroupsFinderFunc.push(finderFunc);
    },

    iterAttached: function(cardDiv, cardId, props, finderFunc, func) {
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
        var maxRight = 1;
        var maxBottom = 1 / cardDiv.data("widthToHeight")(cardId, props);

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            this.iterAttached(cardDiv, cardId, props, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    var attBox = that.getCardBox(attCardDiv, attCardId, attProps);
                    if (attachLeft < 0)
                        minLeft += attachLeft * (attBox.right - attBox.left);
                    else if (attachLeft > 0)
                        maxRight += attachLeft * (attBox.right - attBox.left);

                    if (attachTop < 0)
                        minTop += attachTop * (attBox.bottom - attBox.top);
                    else if (attachTop > 0)
                        maxBottom = + attachTop * (attBox.bottom - attBox.top);
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
        return (cardBox.right - cardBox.left) / (cardBox.bottom - cardBox.top);
    },

    layoutCardGroup: function(cardDiv, cardId, props, layout, zIndex, boxLeft, boxTop, boxWidth, boxHeight, scale) {
        var that = this;
        var cardBox = this.getCardBox(cardDiv, cardId, props);
        var cardWidth = scale * boxWidth / (cardBox.right - cardBox.left);
        var cardHeight = cardWidth / cardDiv.data("widthToHeight")(cardId, props);
        var cardLeft = boxLeft - cardBox.left * cardWidth;
        var cardTop = boxTop - cardBox.top * cardHeight + (boxHeight - cardHeight) / 2;
        this.layoutOneCard(cardDiv, cardId, props, layout, zIndex, cardLeft, cardTop, cardWidth, cardHeight);

        zIndex--;

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            var attachFunc = this.attachedGroupsFinderFunc[i];
            var attachLeft = this.attachedGroupsLeft[i];
            var attachTop = this.attachedGroupsTop[i];

            this.iterAttached(cardDiv, cardId, props, attachFunc,
                function(attCardDiv, attCardId, attProps, layout, attWidthToHeightRatioFunc) {
                    that.layoutCardGroup(attCardDiv, attCardId, attProps, layout, zIndex, cardLeft + cardWidth * attachLeft, cardTop + cardHeight * attachTop,
                        boxWidth - Math.abs(cardWidth * attachLeft), boxHeight - Math.abs(cardHeight*attachTop), scale);
                    zIndex--;
                });
        }
    },

    layoutCardBox: function(cardDiv, cardId, props, layout, boxLeft, boxTop, boxWidth, boxHeight, ratio, scale) {
        var that = this;
        var zIndex = this.zIndexBase;
        this.layoutCardGroup(cardDiv, cardId, props, layout, zIndex, boxLeft, boxTop, boxWidth, boxHeight, scale);
    }
});