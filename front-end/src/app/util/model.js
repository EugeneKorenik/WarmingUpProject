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
    VERTICAL: 'COLUMN',
    HORIZONTAL: 'ROW'
});

export const BorderStyle = Object.freeze({
    SOLID: 'SOLID',
    DASHED: 'DASHED',
    DOTTED: 'DOTTED'
});

export const FILLED_PICTURE_STRUCTURE = [
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

export const FILLED_PICTURE_LIST_STRUCTURE = [
    {
        id: 1,
        name: 'First picture',
        created: Date.now()
    },
    {
        id: 2,
        name: 'Second picture',
        created: Date.now()
    },
    {
        id: 3,
        name: 'Third picture',
        created: Date.now()
    },
    {
        id: 4,
        name: 'Second picture',
        created: Date.now()
    },
    {
        id: 5,
        name: 'Third picture',
        created: Date.now()
    },
    {
        id: 6,
        name: 'Second picture',
        created: Date.now()
    },
    {
        id: 7,
        name: 'Third picture',
        created: Date.now()
    }
];