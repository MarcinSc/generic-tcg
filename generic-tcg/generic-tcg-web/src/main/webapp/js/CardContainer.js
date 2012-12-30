// This is a logical, and not a physical object.
// Its purpose is to maintain order and control over multiple card groups
var CardContainer = Class.extend({
    cardContainerDiv: null,
    cardGroups: null,
    layoutFunc: null,
    cardLayoutFunc: null,
    widthToHeightFunc: null,

    init: function(cardContainerDiv, layoutFunc, cardLayoutFunc, widthToHeightFunc) {
        this.cardContainerDiv = cardContainerDiv;
        this.cardGroups = {};
        this.layoutFunc = layoutFunc;
        this.cardLayoutFunc = cardLayoutFunc;
        this.widthToHeightFunc = widthToHeightFunc;
    },

    /**
     * Layout function called when card is layed out on screen, parameters:
     * cardDiv, cardId, props, cardLeft, cardTop, cardWidth, cardHeight
     *
     * Width to height scale function called to figure out the proportions of the card, parameters:
     * cardId, props
     *
     * @param elem
     * @param cardId
     * @param props
     */
    addCard: function(elem, cardId, props) {
        var cardDiv = $("<div class='card'></div>");
        cardDiv.append(elem);
        this.cardContainerDiv.append(cardDiv);
        cardDiv.data("id", cardId);
        cardDiv.data("props", props);
    },

    removeCard: function(cardIdToRemove) {
        var cardDivToRemove = null;
        this.iterCards(
                function(cardDiv, cardId, props) {
                    if (cardId == cardIdToRemove)
                        cardDivToRemove = cardDiv;
                });

        if (cardDivToRemove != null) {
            cardDivToRemove.remove();
            return true;
        } else {
            return false;
        }
    },

    hasCardId: function(cardIdToFind) {
        var found = false;
        this.iterCards(
                function(cardDiv, cardId, props) {
                    if (cardId == cardIdToFind)
                        found = true;
                });

        return found;
    },

    removeCards: function() {
        $(".card", this.cardContainerDiv).remove();
    },

    addCardGroup: function(name, cardGroup) {
        this.cardGroups[name] = cardGroup;
    },

    setLayout: function(left, top, width, height) {
        this.layoutFunc(this.cardGroups, left, top, width, height);
        this.layoutCards();
    },

    layoutCards: function() {
        var that = this;
        log("CardContainer::layoutCards()");
        iterObj(this.cardGroups,
                function (groupName, cardGroup) {
                    cardGroup.layoutCards(
                            function(cardDiv, cardId, props, zIndex, left, top, width, height) {
                                that.layoutCard(cardDiv, cardId, props, zIndex, left, top, width, height);
                            },
                            function(cardId, props) {
                                return that.widthToHeightFunc(cardId, props);
                            });
                });
    },

    layoutCard: function(cardDiv, cardId, props, zIndex, left, top, width, height) {
        //log("layout card "+cardId+": "+cardLeft+","+cardTop+"    "+cardWidth+"x"+cardHeight);
        cardDiv.css({"zIndex": zIndex,"left": left + "px", "top": top + "px", "width": width, "height": height});
        this.cardLayoutFunc(cardDiv, cardId, props, zIndex, left, top, width, height);
    },

    /**
     * Function called for each card in the CardContainer, parameters:
     * cardDiv, cardId and props function
     *
     * @param func
     */
    iterCards: function(func) {
        $(".card", this.cardContainerDiv).each(
                function() {
                    var cardDiv = $(this);
                    var cardId = cardDiv.data("id");
                    var props = cardDiv.data("props");
                    func(cardDiv, cardId, props);
                });
    },

    getCardsCount: function() {
        return $(".card", this.cardContainerDiv).length;
    }
});