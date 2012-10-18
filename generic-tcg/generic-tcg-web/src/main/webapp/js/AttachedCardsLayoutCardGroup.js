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

    getCardScale: function(cardId, props) {
        return 1;
    },

    getCardRatio: function(cardDiv, cardId, props) {
        return cardDiv.data("widthToHeight")(cardId, props);
    },

    layoutCardBox: function(cardDiv, cardId, props, layout, boxLeft, boxTop, boxWidth, boxHeight, ratio, scale) {
        var cardLeft = boxLeft;
        var cardWidth = boxWidth;
        var cardHeight = cardWidth / ratio;
        var cardTop = boxTop + (boxHeight - cardHeight)/2;
        cardDiv.css({"zIndex": this.zIndexBase,"left": cardLeft + "px", "top": cardTop + "px", "width": cardWidth, "height": cardHeight});
        layout(cardDiv, cardId, props, cardLeft, cardTop, cardWidth, cardHeight);
    }
});