var RowCardLayoutCardGroup = CardGroup.extend({
    padding: 3,
    zIndexBase: 0,

    init: function (cardContainerDiv, cardContainFunc) {
        this._super(cardContainerDiv, cardContainFunc);
    },

    setZIndexBase: function(zIndexBase) {
        this.zIndexBase = zIndexBase;
    },

    layoutCards: function() {
        var ratios = {};
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightRatioFunc) {
                    ratios[cardId] = widthToHeightRatioFunc(cardId, props);
                });

        if (!this.tryLayoutInOneRow(ratios)) {
            var rows = 2;
            while (true) {
                if (this.canLayoutInRows(ratios, rows)) {
                    this.layoutInRows(ratios, rows);
                    return;
                }
                rows++;
            }
        }
    },

    canLayoutInRows: function(ratios, rows) {
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var cardRowCount = 0;
        var row = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightRatioFunc) {
                    var cardWidth = Math.min(1, ratios[cardId]) * rowHeight;
                    cardRowCount++;
                    if (cardRowCount == 1) {
                        ascentLeft += that.padding + cardWidth;
                    } else {
                        if (ascentLeft + that.padding + cardWidth > that.width) {
                            ascentLeft = 0;
                            row++;
                            ascentLeft += that.padding + cardWidth;
                            cardRowCount = 1;
                        } else {
                            ascentLeft += that.padding + cardWidth;
                        }
                    }
                });
        return row < rows;
    },

    layoutInRows: function(ratios, rows) {
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var ascentTop = 0;
        var cardRowCount = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightRatioFunc) {
                    var cardWidth = Math.min(1, ratios[cardId]) * rowHeight;
                    var cardHeight = Math.min(1, 1 / ratios[cardId]) * rowHeight;
                    cardRowCount++;
                    if (cardRowCount == 1) {
                        that.layoutCard(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, cardWidth, cardHeight, rowHeight);
                        ascentLeft += that.padding + cardWidth;
                    } else {
                        if (ascentLeft + that.padding + cardWidth > that.width) {
                            ascentLeft = 0;
                            ascentTop += that.padding + rowHeight;
                            that.layoutCard(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, cardWidth, cardHeight, rowHeight);
                            ascentLeft += that.padding + cardWidth;
                            cardRowCount = 1;
                        } else {
                            that.layoutCard(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, cardWidth, cardHeight, rowHeight);
                            ascentLeft += that.padding + cardWidth;
                        }
                    }
                });
    },

    tryLayoutInOneRow: function(ratios) {
        var totalRatio = 0;
        var cardCount = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightRatioFunc) {
                    cardCount++;
                    totalRatio += Math.min(1, ratios[cardId]);
                });

        var availableCardsWidth = this.width - this.padding * (cardCount - 1);
        var oneRowCardWidths = this.height * totalRatio;
        if (oneRowCardWidths <= availableCardsWidth) {
            this.layoutInOneFullRow(ratios);
            return true;
        } else {
            var cardHeightInTwoRows = this.height / 2 - this.padding;
            var cardHeightToFitAll = availableCardsWidth / totalRatio;
            if (cardHeightToFitAll >= cardHeightInTwoRows) {
                this.layoutInOnePartialRow(ratios, cardHeightToFitAll);
                return true;
            }
        }
        return false;
    },

    layoutInOnePartialRow: function(ratios, rowHeight) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightRatioFunc) {
                    var ratio = ratios[cardId];
                    var cardWidth = Math.min(1, ratio) * rowHeight;
                    var cardHeight = Math.min(1, 1 / ratio) * rowHeight;

                    that.layoutCard(cardDiv, cardId, props, layout, that.left + left, that.top, cardWidth, cardHeight, rowHeight);

                    left += cardWidth + that.padding;
                    index++;
                });
    },

    layoutInOneFullRow: function(ratios) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightRatioFunc) {
                    var ratio = ratios[cardId];
                    var cardWidth = Math.min(1, ratio) * that.height;
                    var cardHeight = Math.min(1, 1 / ratio) * that.height;

                    that.layoutCard(cardDiv, cardId, props, layout, that.left + left, that.top, cardWidth, cardHeight, that.height);

                    left += cardWidth + that.padding;
                    index++;
                });
    },

    layoutCard: function(cardDiv, cardId, props, layout, cardLeft, cardTop, cardWidth, cardHeight, rowHeight) {
        cardTop+=(rowHeight-cardHeight)/2;
        cardDiv.css({"zIndex": this.zIndexBase,"left": cardLeft + "px", "top": cardTop + "px", "width": cardWidth, "height": cardHeight});
        layout(cardDiv, cardId, props, cardLeft, cardTop, cardWidth, cardHeight);
    }
});