var RowCardLayoutCardGroup = CardGroup.extend({
    padding: 3,
    zIndexBase: 0,
    maxCardHeight: null,

    init: function (cardContainerDiv, cardContainFunc) {
        this._super(cardContainerDiv, cardContainFunc);
    },

    setMaxCardHeight: function(maxCardHeight) {
        this.maxCardHeight = maxCardHeight;
    },

    setZIndexBase: function(zIndexBase) {
        this.zIndexBase = zIndexBase;
    },

    getCardHeightScale: function(cardGroupBoxSize) {
        return 1;
    },

    iterCardGroupBoxes: function(func) {
        this.iterCards(func);
    },

    getCardBox: function(widthToHeightFunc, cardDiv, cardId, props) {
        var cardRatio = widthToHeightFunc(cardId, props);
        var result = {};
        result.left = 0;
        result.top = 0;
        result.right = Math.min(1, cardRatio);
        result.bottom = Math.min(1, 1 / cardRatio);
        return result;
    },

    getCardGroupBoxSize: function(widthToHeightFunc, cardDiv, cardId, props) {
        return this.getCardBox(widthToHeightFunc, cardDiv, cardId, props);
    },

    /**
     * Function called to layout cards in group, the function parameters:
     * cardDiv, cardId, props, zIndex, left, top, width, height
     * @param layoutFunc
     */
    layoutCards: function(layoutFunc, widthToHeightFunc) {
        log("RowCardLayoutCardGroup::layoutCards");
        var that = this;
        var scale = 1;
        var cardGroupBoxSizes = {};
        this.iterCardGroupBoxes(
            function(cardDiv, cardId, props) {
                var cardGroupBoxSize = that.getCardGroupBoxSize(widthToHeightFunc, cardDiv, cardId, props);
                cardGroupBoxSizes[cardId] = cardGroupBoxSize;
                scale = Math.min(scale, that.getCardHeightScale(cardGroupBoxSize));
            });

        if (!this.tryLayoutInOneRow(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, scale)) {
            var rows = 2;
            if (this.maxCardHeight != null)
                rows = Math.max(rows, Math.ceil((this.height + this.padding) / (this.maxCardHeight + this.padding)));
            while (true) {
                if (this.canLayoutInRows(cardGroupBoxSizes, rows, scale)) {
                    this.layoutInRows(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, rows, scale);
                    return;
                }
                rows++;
            }
        }
    },

    canLayoutInRows: function(cardGroupBoxSizes, rows, scale) {
        //log("Can layout in rows: "+rows);
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var cardRowCount = 0;
        var row = 0;
        this.iterCardGroupBoxes(
            function(cardDiv, cardId, props) {
                var cardWidth = (cardGroupBoxSizes[cardId].right-cardGroupBoxSizes[cardId].left) * rowHeight * scale;
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

    layoutInRows: function(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, rows, scale) {
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var ascentTop = 0;
        var cardRowCount = 0;
        this.iterCardGroupBoxes(
            function(cardDiv, cardId, props) {
                var ratio = cardGroupBoxSizes[cardId];
                var boxWidth = (ratio.right-ratio.left) * rowHeight * scale;
                cardRowCount++;
                if (cardRowCount == 1) {
                    that.layoutCardGroupBox(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, that.left + ascentLeft, that.top + ascentTop, boxWidth, rowHeight, ratio);
                    ascentLeft += that.padding + boxWidth;
                } else {
                    if (ascentLeft + that.padding + boxWidth > that.width) {
                        ascentLeft = 0;
                        ascentTop += that.padding + rowHeight;
                        that.layoutCardGroupBox(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, that.left + ascentLeft, that.top + ascentTop, boxWidth, rowHeight, ratio);
                        ascentLeft += that.padding + boxWidth;
                        cardRowCount = 1;
                    } else {
                        that.layoutCardGroupBox(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, that.left + ascentLeft, that.top + ascentTop, boxWidth, rowHeight, ratio);
                        ascentLeft += that.padding + boxWidth;
                    }
                }
            });
    },

    tryLayoutInOneRow: function(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, scale) {
        var totalRatio = 0;
        var cardCount = 0;
        this.iterCardGroupBoxes(
            function(cardDiv, cardId, props) {
                cardCount++;
                totalRatio += cardGroupBoxSizes[cardId].right-cardGroupBoxSizes[cardId].left;
            });

        var availableCardsWidth = this.width - this.padding * (cardCount - 1);
        var maxCardHeight = this.height;
        if (this.maxCardHeight != null)
            maxCardHeight = Math.min(this.maxCardHeight, maxCardHeight);
        var oneRowCardWidths = maxCardHeight * totalRatio * scale;
        if (oneRowCardWidths <= availableCardsWidth) {
            this.layoutInOneFullRow(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, scale);
            return true;
        } else {
            var cardHeightInTwoRows = this.height / 2 - this.padding;
            var cardHeightToFitAll = availableCardsWidth / (totalRatio * scale)
            if (cardHeightToFitAll >= cardHeightInTwoRows) {
                this.layoutInOnePartialRow(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, cardHeightToFitAll, scale);
                return true;
            }
        }
        return false;
    },

    layoutInOnePartialRow: function(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, rowHeight, scale) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCardGroupBoxes(
            function(cardDiv, cardId, props) {
                var ratio = cardGroupBoxSizes[cardId];
                var boxWidth = (ratio.right - ratio.left) * rowHeight * scale;

                that.layoutCardGroupBox(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, that.left + left, that.top, boxWidth, rowHeight, ratio);

                left += boxWidth + that.padding;
                index++;
            });
    },

    layoutInOneFullRow: function(layoutFunc, widthToHeightFunc, cardGroupBoxSizes, scale) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCardGroupBoxes(
            function(cardDiv, cardId, props) {
                var ratio = cardGroupBoxSizes[cardId];
                var cardHeight = that.height;
                if (that.maxCardHeight != null)
                    cardHeight = Math.min(cardHeight, that.maxCardHeight);
                var boxWidth = (ratio.right-ratio.left) * cardHeight * scale;

                that.layoutCardGroupBox(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, that.left + left, that.top, boxWidth, cardHeight, ratio);

                left += boxWidth + that.padding;
                index++;
            });
    },

    layoutCardGroupBox: function(layoutFunc, widthToHeightFunc, cardDiv, cardId, props, boxLeft, boxTop, boxWidth, boxHeight, ratio) {
        var cardLeft = boxLeft;
        var cardWidth = boxWidth;
        var cardHeight = cardWidth / widthToHeightFunc(cardId, props);
        var cardTop = boxTop + (boxHeight - cardHeight) / 2;
        layoutFunc(cardDiv, cardId, props, this.zIndexBase, cardLeft, cardTop, cardWidth, cardHeight);
    }
});