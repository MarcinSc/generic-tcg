var SampleGameUi = Class.extend({
    cardContainerDiv: null,
    cardContainer: null,

    init: function(cardContainerDiv, cardGroup) {
        this.cardContainerDiv = cardContainerDiv;
        this.cardContainer = new CardContainer(this.cardContainerDiv, this.cardContainerLayoutFunction);
        this.cardContainer.addCardGroup("main", cardGroup);
    },

    addCard: function(cardId, props) {
        var elem;
        if (props["hor"]==true)
            elem = $("<img class='cardImg' src='images/horizontal.png'>");
        else
            elem = $("<img class='cardImg' src='images/vertical.png'>");
        this.cardContainer.addCard(elem, cardId, props, this.layoutCardBox, this.widthToHeightScale);
    },

    layoutCardBox: function(cardDiv, cardId, props, left, top, width, height) {
    },

    widthToHeightScale: function(cardId, props) {
        if (props["hor"]==true)
            return 500/360;
        else        var width = $(window).width();
        var height = $(window).height();

        var innerWidth = width - 6;
        var innerHeight = height - 6;

        if (innerHeight < 300)
            innerHeight = 300;
        if (innerWidth < 400)
            innerWidth = 400;

            return 360/500;
    },

    layoutCards: function() {
        this.cardContainer.layoutCards();
    },

    cardContainerLayoutFunction : function(cardGroups, left, top, width, height) {
        var padding = 3;
        cardGroups["main"].setLayout(left+padding, top+padding, width-2*padding, height-2*padding);
    },

    layoutGame: function(width, height) {
//        var padding = this.cardContainerDiv.padding();
//        var innerWidth = width-padding.left-padding.right;
//        var innerHeight = height-padding.top-padding.bottom;
        //this.cardContainer.setLayout(padding.left, padding.top, innerWidth, innerHeight);
//        this.cardContainerDiv.css({"width": width, "height": height});
        this.cardContainer.setLayout(0, 0, width, height);
    }
});