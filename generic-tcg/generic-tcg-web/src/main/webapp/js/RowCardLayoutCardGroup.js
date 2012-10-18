var RowCardLayoutCardGroup = CardGroup.extend({
    padding: 3,
    zIndexBase: 0,

    init: function (cardContainerDiv, cardContainFunc) {
        this._super(cardContainerDiv, cardContainFunc);
    },

    setZIndexBase: function(zIndexBase) {
        this.zIndexBase = zIndexBase;
    },

    getCardHeightScale: function(cardDiv, cardId, props) {
        return 1;
    },

    getCardBoxRatio: function(cardDiv, cardId, props) {
        return cardDiv.data("widthToHeight")(cardId, props);
    },

    layoutCards: function() {
        var that = this;
        var ratios = {};
        var scale = 1;
        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    ratios[cardId] = that.getCardBoxRatio(cardDiv, cardId, props);
                    scale = Math.min(scale, that.getCardHeightScale(cardDiv, cardId, props));
                });

        if (!this.tryLayoutInOneRow(ratios, scale)) {
            var rows = 2;
            while (true) {
                if (this.canLayoutInRows(ratios, rows, scale)) {
                    this.layoutInRows(ratios, rows, scale);
                    return;
                }
                rows++;
            }
        }
    },

    canLayoutInRows: function(ratios, rows, scale) {
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var cardRowCount = 0;
        var row = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    var cardWidth = Math.min(1, ratios[cardId]) * rowHeight * scale;
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

    layoutInRows: function(ratios, rows, scale) {
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var ascentTop = 0;
        var cardRowCount = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    var ratio = ratios[cardId];
                    var boxWidth = Math.min(1, ratio) * rowHeight * scale;
                    cardRowCount++;
                    if (cardRowCount == 1) {
                        that.layoutCardBox(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, boxWidth, rowHeight, ratio, scale);
                        ascentLeft += that.padding + boxWidth;
                    } else {
                        if (ascentLeft + that.padding + boxWidth > that.width) {
                            ascentLeft = 0;
                            ascentTop += that.padding + rowHeight;
                            that.layoutCardBox(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, boxWidth, rowHeight, ratio, scale);
                            ascentLeft += that.padding + boxWidth;
                            cardRowCount = 1;
                        } else {
                            that.layoutCardBox(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, boxWidth, rowHeight, ratio, scale);
                            ascentLeft += that.padding + boxWidth;
                        }
                    }
                });
    },

    tryLayoutInOneRow: function(ratios, scale) {
        var totalRatio = 0;
        var cardCount = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    cardCount++;
                    totalRatio += Math.min(1, ratios[cardId]);
                });

        var availableCardsWidth = this.width - this.padding * (cardCount - 1);
        var oneRowCardWidths = this.height * totalRatio * scale;
        if (oneRowCardWidths <= availableCardsWidth) {
            this.layoutInOneFullRow(ratios, scale);
            return true;
        } else {
            var cardHeightInTwoRows = this.height / 2 - this.padding;
            var cardHeightToFitAll = availableCardsWidth / (totalRatio * scale)
            if (cardHeightToFitAll >= cardHeightInTwoRows) {
                this.layoutInOnePartialRow(ratios, cardHeightToFitAll, scale);
                return true;
            }
        }
        return false;
    },

    layoutInOnePartialRow: function(ratios, rowHeight, scale) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    var ratio = ratios[cardId];
                    var boxWidth = Math.min(1, ratio) * rowHeight * scale;

                    that.layoutCardBox(cardDiv, cardId, props, layout, that.left + left, that.top, boxWidth, rowHeight, ratio, scale);

                    left += boxWidth + that.padding;
                    index++;
                });
    },

    layoutInOneFullRow: function(ratios, scale) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout) {
                    var ratio = ratios[cardId];
                    var boxWidth = Math.min(1, ratio) * that.height * scale;

                    that.layoutCardBox(cardDiv, cardId, props, layout, that.left + left, that.top, boxWidth, that.height, ratio, scale);

                    left += boxWidth + that.padding;
                    index++;
                });
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