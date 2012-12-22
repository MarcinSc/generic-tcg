var SampleGameUi = Class.extend({
    cardContainerDiv: null,
    cardContainer: null,

    init: function(cardContainerDiv, cardGroup) {
        this.cardContainerDiv = cardContainerDiv;
        this.cardContainer = new CardContainer(this.cardContainerDiv, this.cardContainerLayoutFunction);
        this.cardContainer.addCardGroup("main", cardGroup);
    },

    getCardImg: function(cardId, props) {
        if (props["hor"]==true)
            return "images/horizontal.png";
//            return "http://lotrtcgwiki.com/wiki/_media/cards:lotr01331.jpg";
        else
            return "images/vertical.png";
//            return "http://lotrtcgwiki.com/wiki/_media/cards:lotr01013.jpg";
    },

    addCard: function(cardId, props) {
        var elem = $("<img class='cardImg' src='"+this.getCardImg(cardId, props)+"'>");
        this.cardContainer.addCard(elem, cardId, props, this.layoutCardBox, this.widthToHeightScale);
    },

    layoutCardBox: function(cardDiv, cardId, props, left, top, width, height) {
    },

    widthToHeightScale: function(cardId, props) {
        if (props["hor"]==true)
            return 500/360;
        else
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