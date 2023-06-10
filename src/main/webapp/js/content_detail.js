class MetricNode {
    previous = [];

    constructor(value, row, column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }
}

function getMinNodes(levenshteinArr, row, col, allowModification = true) {
    let result = [];
    // modification
    if (allowModification && row > 0 && col > 0) {
        result.push(levenshteinArr[row - 1][col - 1]);
    }

    // insertion
    if (row > 0) {
        result.push(levenshteinArr[row - 1][col]);
    }

    // deletion
    if (col > 0) {
        result.push(levenshteinArr[row][col - 1]);
    }

    const valueArr = result.map(node => node.value);
    const min = Math.min(...valueArr);

    return result.filter(node => node.value === min);
}

/**
 * @param {array<string>} oldContentArr
 * @param {array<string>} newContentArr
 * @return {array<array<MetricNode>>} dinamic programming array for levenshtein distance
 */
function levenshteinDistance(oldContentArr, newContentArr, allowModification = true) {
    let levenshteinArr = [];
    for (let i = 0; i <= oldContentArr.length; i++) {
        let row = [];
        for (let j = 0; j <= newContentArr.length; j++) {
            row.push(new MetricNode(0, i, j));
        }
        levenshteinArr.push(row);
    }

    for (let i = 0; i <= oldContentArr.length; i++) {
        levenshteinArr[i][0].value = i;
        if (i > 0) levenshteinArr[i][0].previous.push(levenshteinArr[i - 1][0]);
    }
    for (let j = 0; j <= newContentArr.length; j++) {
        levenshteinArr[0][j].value = j;
        if (j > 0) levenshteinArr[0][j].previous.push(levenshteinArr[0][j - 1]);
    }

    for (let i = 1; i <= oldContentArr.length; i++) {
        for (let j = 1; j <= newContentArr.length; j++) {
            const minNodes = getMinNodes(levenshteinArr, i, j, allowModification);
            const minNode = minNodes[0];
            const min = minNode.value;
            if (oldContentArr[i - 1] === newContentArr[j - 1]) {
                const previous = levenshteinArr[i - 1][j - 1];
                levenshteinArr[i][j].previous = [previous];
                levenshteinArr[i][j].value = previous.value;
            } else {
                levenshteinArr[i][j].value = min + 1;
                levenshteinArr[i][j].previous = minNodes;

                console.log(`${oldContentArr[i - 1]} != ${newContentArr[j - 1]}, previous: ${minNodes.map(node => `[${node.row}][${node.column}]`)}`);
            }

        }
    }
    return levenshteinArr;
}

const WordDifferenceHandler = (function () {
    const differenceType = {
        DELETION: 1,
        INSERTION: 2,
        MODIFICATION: 3,
        NO_CHANGE: 4
    };

    const createDiffElements = (word, diffType) => {
        return {word: word, diffType: diffType};
    };

    const getDiffType = (diffElement) => {
        return diffElement.diffType;
    }

    const getWord = (diffElement) => {
        return diffElement.word;
    }

    /**
     * @return {string}
     */
    const createWordDiffBlock = (wordsWithSameDiffType, diffType) => {
        let result = wordsWithSameDiffType.join(" ");
        switch (diffType) {
            case differenceType.DELETION:
                result = "<span class='deleted-words'>" + result + "</span>";
                break;
            case differenceType.INSERTION:
                result = "<span class='inserted-words'>" + result + "</span>";
                break;
            case differenceType.MODIFICATION:
                result = "<span class='modified-words'>" + result + "</span>";
                break;
        }
        return result;
    }

    /**
     * @return {string}
     */
    const createWordDiffBlocksByDiffElement = (diffElements) => {
        let lastDiffType = getDiffType(diffElements[0]);
        const result = [];
        const previousWordsSameDiffType = [];

        for (let i = 0; i < diffElements.length; i++) {
            const curDiffElement = diffElements[i];
            const curDiffType = getDiffType(curDiffElement);
            const curWord = getWord(curDiffElement);
            if (curDiffType === lastDiffType) {
                previousWordsSameDiffType.push(curWord);
            } else {
                result.push(createWordDiffBlock(previousWordsSameDiffType, lastDiffType));
                previousWordsSameDiffType.splice(0, previousWordsSameDiffType.length);
                previousWordsSameDiffType.push(curWord);
            }
            lastDiffType = curDiffType;
        }
        result.push(createWordDiffBlock(previousWordsSameDiffType, lastDiffType));
        return result.join(" ");
    }

    /**
     * @param {array<MetricNode>} levenshteinPath
     * @param {array<string>} oldWords
     * @param {array<string>} newWords
     * @return {{oldElements: string, newElements: string}}
     */
    const createWordDiffInParagraphText = function (levenshteinPath, oldWords, newWords) {
        const oldDiffElements = [];
        const newDiffElements = [];

        for (let i = levenshteinPath.length - 1; i > 0; i--) {
            const curNode = levenshteinPath[i];
            const prevNode = levenshteinPath[i - 1];

            //deletion
            if (curNode.column === prevNode.column) {
                oldDiffElements.push(createDiffElements(oldWords[curNode.row - 1], differenceType.DELETION));
            }
            //insertion
            else if (curNode.row === prevNode.row) {
                newDiffElements.push(createDiffElements(newWords[curNode.column - 1], differenceType.INSERTION));
            }
            // modification or no change
            else if (curNode.column === prevNode.column + 1 && curNode.row === prevNode.row + 1) {
                if (curNode.value === prevNode.value) {
                    //no change
                    oldDiffElements.push(createDiffElements(oldWords[curNode.row - 1], differenceType.NO_CHANGE));
                    newDiffElements.push(createDiffElements(newWords[curNode.column - 1], differenceType.NO_CHANGE));
                } else {
                    //modification
                    oldDiffElements.push(createDiffElements(oldWords[curNode.row - 1], differenceType.MODIFICATION));
                    newDiffElements.push(createDiffElements(newWords[curNode.column - 1], differenceType.MODIFICATION));
                }
            }
        }

        oldDiffElements.reverse();
        newDiffElements.reverse();

        return {
            oldElements: createWordDiffBlocksByDiffElement(oldDiffElements),
            newElements: createWordDiffBlocksByDiffElement(newDiffElements)
        };
    }

    /**
     * @param oldWords {array<string>}
     * @param newWords {array<string>}
     * @return {array<MetricNode>}
     */
    const createLevenshteinPath = (oldWords, newWords) => {
        const levenshteinArr = levenshteinDistance(oldWords, newWords, false);

        const path = [];
        let curNode = levenshteinArr[oldWords.length][newWords.length];
        while (curNode.previous.length > 0) {
            path.push(curNode);
            curNode = curNode.previous[0];
        }
        path.push(curNode);

        return path.reverse();
    }

    const splitParagraphToWords = (paragraph) => {
        return paragraph.split(" ");
    }

    return {
        createWordDifferenceElements: createWordDiffInParagraphText,
        splitParagraphToWords: splitParagraphToWords,
        createLevenshteinPath: createLevenshteinPath
    }
})();


function createParagraphDifferenceElements(path, oldParagraphs, newParagraph) {
    let result = {
        oldElements: [],
        newElements: []
    };
    for (let i = path.length - 1; i > 0; i--) {
        const curNode = path[i];
        const prevNode = path[i - 1];
        const curOldParagraph = document.createElement("p");
        const curNewParagraph = document.createElement("p");

        //deletion
        if (curNode.column === prevNode.column) {
            curOldParagraph.innerHTML = oldParagraphs[curNode.row - 1];
            curOldParagraph.classList.add("deleted-paragraph");
        }
        //insertion
        else if (curNode.row === prevNode.row) {
            curNewParagraph.innerHTML = newParagraph[curNode.column - 1];
            curNewParagraph.classList.add("inserted-paragraph");
        }
        // modification or no change
        else if (curNode.column === prevNode.column + 1 && curNode.row === prevNode.row + 1) {
            if (curNode.value === prevNode.value) {
                //no change
                curOldParagraph.innerHTML = oldParagraphs[curNode.row - 1];
                curNewParagraph.innerHTML = newParagraph[curNode.column - 1];

            } else {
                //modification
                const curOldWords =
                    WordDifferenceHandler.splitParagraphToWords(oldParagraphs[curNode.row - 1]);

                const curNewWords =
                    WordDifferenceHandler.splitParagraphToWords(newParagraph[curNode.column - 1]);

                const curLevenshteinPath =
                    WordDifferenceHandler.createLevenshteinPath(curOldWords, curNewWords);

                const curWordDiffElements =
                    WordDifferenceHandler.createWordDifferenceElements(curLevenshteinPath, curOldWords, curNewWords);

                curOldParagraph.innerHTML = curWordDiffElements.oldElements;
                curNewParagraph.innerHTML = curWordDiffElements.newElements;

                curOldParagraph.classList.add("deleted-paragraph");
                curNewParagraph.classList.add("inserted-paragraph");
            }
        } else {
            throw new Error(`Invalid path: (${curNode.row}, ${curNode.column}) -> (${prevNode.row}, ${prevNode.column})`);
        }

        result["oldElements"].push(curOldParagraph);
        result["newElements"].push(curNewParagraph);
    }
    result["oldElements"].reverse();
    result["newElements"].reverse();
    return result;

}

/**
 * @param {array<array<MetricNode>>} levenshteinArr dynamic programming array for levenshtein distance
 * @return {array<MetricNode>} path from start to end
 */
function getLevenshteinPath(levenshteinArr) {
    let path = [];
    let current = levenshteinArr[levenshteinArr.length - 1][levenshteinArr[0].length - 1];
    while (current.previous.length > 0) {
        path.push(current);
        current = current.previous[0];
    }
    path.push(current);
    return path.reverse();
}


document.addEventListener("DOMContentLoaded", () => {
    const oldMultiline = document.getElementById("oldMultiline");
    const newMultiline = document.getElementById("newMultiline");

    if (oldMultiline && newMultiline) {
        const oldParagraphEntries = oldMultiline.querySelectorAll("p").entries();
        let oldParagraphs = [];
        for (let entry of oldParagraphEntries) {
            oldParagraphs.push(entry[1].innerHTML);
        }

        const newParagraphEntries = newMultiline.querySelectorAll("p").entries();
        let newParagraphs = [];
        for (let entry of newParagraphEntries) {
            newParagraphs.push(entry[1].innerHTML);
        }

        const levenshteinArr = levenshteinDistance(oldParagraphs, newParagraphs);
        const path = getLevenshteinPath(levenshteinArr);
        const result = createParagraphDifferenceElements(path, oldParagraphs, newParagraphs);

        const oldElements = result["oldElements"];
        const newElements = result["newElements"];

        oldMultiline.innerHTML = "";
        oldMultiline.append(...oldElements);

        newMultiline.innerHTML = "";
        newMultiline.append(...newElements);
    }
});
