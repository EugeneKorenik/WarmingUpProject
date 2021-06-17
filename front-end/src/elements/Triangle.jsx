import React from 'react';
import { DragSource, DropTarget } from "react-dnd";
import { ElementType } from '../model';
import * as DndSpecifications from '../dndSpecifications';

class DraggableTriangle extends React.Component {

    constructor(props) {
        super(props);

        let colorHex = props.colorHex;
        if (!colorHex) {
            colorHex = "#00FFFF";
        }

        this.state = {
            colorHex: colorHex,
        };
    }

    updateColor = (event) => {
        const oldColorValue = this.state.colorHex;
        const newColorValue = event.target.value;

        if (oldColorValue !== newColorValue) {
            this.setState({
                colorHex: newColorValue
            });
        }
    }

    render() {
        const { connectDragSource, isDragging } = this.props;
        const style = {
            borderBottomColor: this.state.colorHex,
            opacity: isDragging && this.props.id ? 0 : 1
        };

        return connectDragSource(
            <div id={this.props.id} className="triangle element" style={style}>
                <input type="color" onChange={this.updateColor} value={this.state.colorHex} />
            </div>
        );
    }
}

class Triangle extends React.Component {

    render() {
        const { connectDropTarget } = this.props;
        return connectDropTarget(
            <div>
                <DraggableTriangle {...this.props} />
            </div>
        );
    }
}

DraggableTriangle = DragSource(ElementType.TRIANGLE, DndSpecifications.dragSpecification, DndSpecifications.collectSource)(DraggableTriangle);
Triangle = DropTarget(ElementType.ALL, DndSpecifications.dropSpecification, DndSpecifications.collectTarget)(Triangle);

export { DraggableTriangle, Triangle };