import { ElementType } from "./model";

export const dropSpecification = {
    drop: (props, monitor, component) => {
        if (!monitor.didDrop()) {
            const item = monitor.getItem();
            const droppableElementId = props.id;
            if (droppableElementId !== item.id) {
                props.moveElement(droppableElementId, item);
            }
        }
    }
};

export const dragSpecification = {
    beginDrag: (props, monitor, component) => {
        const object = props.object;
        if(!object) {
            return {
                id: getIdentifier(props),
                type: getElementType(component),
                index: 0
            }
        }
        return object;
    }
}

export const collectSource = (connect, monitor) => {
    return {
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    };
};

export const collectTarget = (connect, monitor) => {
    return {
        connectDropTarget: connect.dropTarget()
    };
};

function getElementType(component) {
    const componentName = component.constructor.name.toLowerCase();
    if(componentName.includes(ElementType.CIRCLE)) {
        return ElementType.CIRCLE;
    } else if(componentName.includes(ElementType.TRIANGLE)) {
        return ElementType.TRIANGLE;
    } else if(componentName.includes(ElementType.SQUARE)) {
        return ElementType.SQUARE;
    } else if(componentName.includes(ElementType.GROUP)) {
        return ElementType.GROUP;
    }
    return componentName;
}

let currentIdentifier = 55;
function getIdentifier(props) {
    return props.id ? props.id : currentIdentifier++;
}

