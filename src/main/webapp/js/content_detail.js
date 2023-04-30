class MetricNode {
    previous = [];

    constructor(value, row, column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }
}

function getMinNodes(levenshteinArr, row, col) {
    let result = [];
    // modification
    if (row > 0 && col > 0) {
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

function levenshteinDistance(oldContentArr, newContentArr) {
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
            const minNodes = getMinNodes(levenshteinArr, i, j);
            const minNode = minNodes[0];
            const min = minNode.value;
            if (oldContentArr[i - 1] === newContentArr[j - 1]) {
                const previous = levenshteinArr[i - 1][j - 1];
                levenshteinArr[i][j].previous = [previous];
                levenshteinArr[i][j].value = previous.value;
            } else {
                levenshteinArr[i][j].value = min + 1;
                levenshteinArr[i][j].previous = minNodes;
            }

        }
    }
    return levenshteinArr;
}

function createLevenshteinParagraphElements(path, oldParagraphs, newParagraph) {
    let result = [];
    for (let i = path.length - 1; i > 0; i--) {
        const curNode = path[i];
        const prevNode = path[i - 1];
        const curElement = document.createElement("p");

        //deletion
        if (curNode.column === prevNode.column) {
            continue;
            //curElement.innerHTML = oldParagraphs[curNode.row - 1];
            //curElement.classList.add("deleted");
        }
        //insertion
        else if (curNode.row === prevNode.row) {
            curElement.innerHTML = newParagraph[curNode.column - 1];
            curElement.classList.add("inserted");
        }
        //modification
        else if (curNode.column === prevNode.column + 1 && curNode.row === prevNode.row + 1) {
            if (curNode.value === prevNode.value) {
                curElement.innerHTML = newParagraph[curNode.column - 1];
            } else {
                curElement.innerHTML = newParagraph[curNode.column - 1];
                curElement.classList.add("modified");
            }
        } else {
            throw new Error(`Invalid path: (${curNode.row}, ${curNode.column}) -> (${prevNode.row}, ${prevNode.column})`);
        }
        result.push(curElement);
    }
    return result.reverse();

}

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

    console.log(oldParagraphs);
    console.log(newParagraphs);

    const levenshteinArr = levenshteinDistance(oldParagraphs, newParagraphs);
    const path = getLevenshteinPath(levenshteinArr);
    const result = createLevenshteinParagraphElements(path, oldParagraphs, newParagraphs);

    newMultiline.innerHTML = "";
    newMultiline.append(...result)

}