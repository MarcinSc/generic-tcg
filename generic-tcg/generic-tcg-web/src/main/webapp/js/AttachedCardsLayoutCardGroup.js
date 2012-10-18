var AttachedCardsLayoutCardGroup = CardGroup.extend({
    padding: 3,
    zIndexBase: 0,
    attachedGroupsLeft: null,
    attachedGroupsTop: null,
    attachedGroupsFinderFunc: null,

    init:function (cardContainerDiv, cardContainFunc) {
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

    setZIndexBase: function(zIndexBase) {
        this.zIndexBase = zIndexBase;
    },

    getAttachedCardsLeftInc: function(cardDiv, cardId, props) {
        var that = this;
        var result = 0;
        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            if (that.attachedGroupsLeft[i] < 0) {
                this.iterAttached(cardDiv, cardId, props, this.attachedGroupsFinderFunc[i],
                        function (cardDivAtt, cardIdAtt, propsAtt) {
                            result += -that.attachedGroupsLeft[i]
                                    + that.getAttachedCardsLeftInc(cardDivAtt, cardIdAtt, propsAtt);
                        });
            }
        }
        return result;
    },

    getAttachedCardsRightInc: function(cardDiv, cardId, props) {
        var that = this;
        var result = 0;
        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            if (that.attachedGroupsLeft[i] > 0) {
                this.iterAttached(cardDiv, cardId, props, this.attachedGroupsFinderFunc[i],
                        function (cardDivAtt, cardIdAtt, propsAtt) {
                            result += that.attachedGroupsLeft[i]
                                    + that.getAttachedCardsRightInc(cardDivAtt, cardIdAtt, propsAtt);
                        });
            }
        }
        return result;
    },

    getAttachedCardsTopInc: function(cardDiv, cardId, props) {
        var that = this;
        var heightResult = 0;
        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            if (that.attachedGroupsTop[i] < 0) {
                this.iterAttached(cardDiv, cardId, props, this.attachedGroupsFinderFunc[i],
                        function (cardDivAtt, cardIdAtt, propsAtt) {
                            heightResult += -that.attachedGroupsTop[i]
                                    + that.getAttachedCardsTopInc(cardDivAtt, cardIdAtt, propsAtt);
                        });
            }
        }
        return heightResult;
    },

    getAttachedCardsBottomInc: function(cardDiv, cardId, props) {
        var that = this;
        var heightResult = 0;
        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            if (that.attachedGroupsTop[i] > 0) {
                this.iterAttached(cardDiv, cardId, props, this.attachedGroupsFinderFunc[i],
                        function (cardDivAtt, cardIdAtt, propsAtt) {
                            heightResult += that.attachedGroupsTop[i]
                                    + that.getAttachedCardsBottomInc(cardDivAtt, cardIdAtt, propsAtt);
                        });
            }
        }
        return heightResult;
    },

    layoutCards: function() {
        var that = this;
        var scales = {};
        var leftIncs = {};
        var rightIncs = {};
        var topIncs = {};
        var bottomIncs = {};
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightScaleFunc) {
                    var leftInc = that.getAttachedCardsLeftInc(cardDiv, cardId, props);
                    var rightInc = that.getAttachedCardsRightInc(cardDiv, cardId, props);

                    var topInc = that.getAttachedCardsTopInc(cardDiv, cardId, props);
                    var bottomInc = that.getAttachedCardsBottomInc(cardDiv, cardId, props);

                    scales[cardId] = widthToHeightScaleFunc(cardId, props);
                    leftIncs[cardId] = leftInc;
                    rightIncs[cardId] = rightInc;
                    topIncs[cardId] = topInc;
                    bottomIncs[cardId] = bottomInc;
                });

        if (!this.tryLayoutInOneRow(scales, leftIncs, rightIncs, topIncs, bottomIncs)) {
            var rows = 2;
            while (true) {
                if (this.canLayoutInRows(scales, leftIncs, rightIncs, topIncs, bottomIncs, rows)) {
                    this.layoutInRows(scales, leftIncs, rightIncs, topIncs, bottomIncs, rows);
                    return;
                }
                rows++;
            }
        }
    },

    canLayoutInRows: function(scales, heights, rows) {
        var that = this;
        var cardHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var cardRowCount = 0;
        var row = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightScaleFunc) {
                    var cardWidth = Math.min(1, scales[cardId]) * cardHeight;
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

    layoutInRows: function(scales, heights, rows) {
        var that = this;
        var rowHeight = this.height / rows - this.padding * (rows - 1);
        var ascentLeft = 0;
        var ascentTop = 0;
        var cardRowCount = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightScaleFunc) {
                    var cardWidth = Math.min(1, scales[cardId]) * rowHeight;
                    var cardHeight = Math.min(1, 1 / scales[cardId]) * rowHeight;
                    cardRowCount++;
                    if (cardRowCount == 1) {
                        that.layoutCardBox(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, cardWidth, cardHeight);
                        ascentLeft += that.padding + cardWidth;
                    } else {
                        if (ascentLeft + that.padding + cardWidth > that.width) {
                            ascentLeft = 0;
                            ascentTop += that.padding + rowHeight;
                            that.layoutCardBox(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, cardWidth, cardHeight);
                            ascentLeft += that.padding + cardWidth;
                            cardRowCount = 1;
                        } else {
                            that.layoutCardBox(cardDiv, cardId, props, layout, that.left + ascentLeft, that.top + ascentTop, cardWidth, cardHeight);
                            ascentLeft += that.padding + cardWidth;
                        }
                    }
                });
    },

    tryLayoutInOneRow: function(scales, leftIncs, rightIncs, topIncs, bottomIncs) {
        var totalScale = 0;
        var maxHeight = 1;
        var cardCount = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightScaleFunc) {
                    cardCount++;
                    totalScale += Math.min(1, scales[cardId]);
                    maxHeight = Math.max(maxHeight, 1 + topIncs[cardId] + bottomIncs[cardId]);
                });
        // Scale it down by whatever consumes most height room
        totalScale /= maxHeight;

        var availableCardsWidth = this.width - this.padding * (cardCount - 1);
        var oneRowCardWidths = this.height * totalScale;
        if (oneRowCardWidths <= availableCardsWidth) {
            this.layoutInOneFullRow(scales, leftIncs, rightIncs, topIncs, bottomIncs, maxHeight);
            return true;
        } else {
            var cardHeightInTwoRows = this.height / 2 - this.padding;
            var cardHeightToFitAll = availableCardsWidth / totalScale;
            if (cardHeightToFitAll >= cardHeightInTwoRows) {
                this.layoutInOnePartialRow(scales, scales, maxHeight, cardHeightToFitAll);
                return true;
            }
        }
        return false;
    },

    layoutInOnePartialRow: function(scales, heights, maxHeight, height) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightScaleFunc) {
                    var scale = scales[cardId];
                    var cardWidth = Math.min(1, scale) * height;
                    var cardHeight = Math.min(1, 1 / scale) * height;

                    that.layoutCardBox(cardDiv, cardId, props, layout, that.left + left, that.top, cardWidth, cardHeight);

                    left += cardWidth + that.padding;
                    index++;
                });
    },

    layoutInOneFullRow: function(scales, leftIncs, rightIncs, topIncs, bottomIncs, maxHeight) {
        var that = this;
        var left = 0;
        var index = 0;
        this.iterCards(
                function(cardDiv, cardId, props, layout, widthToHeightScaleFunc) {
                    var boxWidth = that.height * (Math.min(1, scales[cardId]) + leftIncs[cardId] + rightIncs[cardId]);

                    that.layoutCardInBox(cardDiv, cardId, props, layout, scales[cardId], that.left + left, that.top, boxWidth, that.height, 
                            leftIncs[cardId], rightIncs[cardId], topIncs[cardId], bottomIncs[cardId], 1 / maxHeight);

                    left += boxWidth + that.padding;
                    index++;
                });
    },

    layoutCardInBox: function(cardDiv, cardId, props, layout, cardScale, boxLeft, boxTop, boxWidth, boxHeight,
                              leftInc, rightInc, topInc, bottomInc, sizeMultiplier) {
        var zIndex = this.zIndexBase;

        log(leftInc+" "+rightInc+" "+topInc+" "+bottomInc);

        var mainCardPosX = boxLeft + sizeMultiplier * boxHeight * leftInc;
        var mainCardPosY = boxTop + sizeMultiplier * boxHeight * topInc;
        var mainCardWidth = sizeMultiplier * boxHeight * Math.min(1, cardScale);
        var mainCardHeight = sizeMultiplier * boxHeight * Math.min(1, 1 / cardScale);

        cardDiv.css({"zIndex": zIndex, "left": mainCardPosX + "px", "top": mainCardPosY + "px", "width": mainCardWidth, "height": mainCardHeight});
        layout(cardDiv, cardId, props, mainCardPosX, mainCardPosY, mainCardWidth, mainCardHeight);

        for (var i = 0; i < this.attachedGroupsFinderFunc.length; i++) {
            if (that.attachedGroupsLeft[i] > 0) {
                this.iterAttached(cardDiv, cardId, props, this.attachedGroupsFinderFunc[i],
                        function (cardDivAtt, cardIdAtt, propsAtt) {
                            result += that.attachedGroupsLeft[i]
                                    + that.getAttachedCardsRightInc(cardDivAtt, cardIdAtt, propsAtt);
                        });
            }
        }
    }
});