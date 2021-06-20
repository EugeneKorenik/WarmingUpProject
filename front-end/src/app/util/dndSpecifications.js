import { ElementType } from "./model";

export const dragSpecification = {
    beginDrag: (props, monitor, component) => {
        let item = props.object;
        if(!item) {
            item = {
                id: props.id,
                type: getElementType(component),
            }

            Object.assign(item, component.state);
            return item;
        }
        return item;
    }
}


export const dropSpecification = {
    drop: (props, monitor, component) => {
        if (!monitor.didDrop()) {
            const draggableItem = monitor.getItem();
            const droppableElementId = props.object?.id;
            props.moveElement(droppableElementId, draggableItem);
        }
    }
};

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

