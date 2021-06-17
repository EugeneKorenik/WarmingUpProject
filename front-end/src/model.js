export const ElementType = Object.freeze({
    SQUARE: 'square',
    TRIANGLE: 'triangle',
    CIRCLE: 'circle',
    GROUP: 'group',
    ALL: [
        'square',
        'triangle',
        'circle',
        'group'
    ]
});

export const GroupType = Object.freeze({
    VERTICAL: 'column',
    HORIZONTAL: 'row'
});

export const BorderStyle = Object.freeze({
    SOLID: 'solid',
    DASHED: 'dashed',
    DOTTED: 'dotted'
});

export const STRUCTURE = [];

export const FILLED_STRUCTURE = [
    {
        id: 2,
        type: ElementType.CIRCLE
    },
    {
        id: 3,
        type: ElementType.SQUARE
    },
    {
        id: 4,
        type: ElementType.TRIANGLE
    },
    {
        id: 5,
        type: ElementType.GROUP,
        elements: [
            {
                id: 6,
                type: ElementType.CIRCLE
            }
        ]
    }

];