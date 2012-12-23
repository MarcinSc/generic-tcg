// This is a logical, and not a physical object.
// Its purpose is to maintain order and control over multiple card groups
var CardContainer = Class.extend({
    cardContainerDiv: null,
    cardGroups: null,
    layoutFunc: null,

    init: function(cardContainerDiv, layoutFunc) {
        this.cardContainerDiv = cardContainerDiv;
        this.cardGroups = {};
        this.layoutFunc = layoutFunc;
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
     * @param layoutFunc
     * @param widthToHeightScaleFunc
     */
    addCard: function(elem, cardId, props, layoutFunc, widthToHeightScaleFunc) {
        var cardDiv = $("<div class='card'></div>");
        cardDiv.append(elem);
        this.cardContainerDiv.append(cardDiv);
        cardDiv.data("id", cardId);
        cardDiv.data("props", props);
        cardDiv.data("layout", layoutFunc);
        cardDiv.data("widthToHeight", widthToHeightScaleFunc);
    },

    removeCard: function(cardIdToRemove) {
        var cardDivToRemove = null;
        this.iterCards(
                function(cardDiv, cardId, props, layoutFunc) {
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
                function(cardDiv, cardId, props, layout) {
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
    },

    layoutCards: function() {
        log("CardContainer::layoutCards()");
        iterObj(this.cardGroups,
                function (groupName, cardGroup) {
                    cardGroup.layoutCards();
                });
    },

    /**
     * Function called for each card in the CardContainer, parameters:
     * cardDiv, cardId, props and layout function
     * 
     * @param func
     */
    iterCards: function(func) {
        $(".card", this.cardContainerDiv).each(
                function() {
                    var cardDiv = $(this);
                    var cardId = cardDiv.data("id");
                    var props = cardDiv.data("props");
                    var layout = cardDiv.data("layout");
                    func(cardDiv, cardId, props, layout);
                });
    },

    getCardsCount: function() {
        return $(".card", this.cardContainerDiv).length;
    }
});