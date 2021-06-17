import React from 'react';
import { DragSource, DropTarget } from "react-dnd";
import { BorderStyle, ElementType } from '../model';
import * as DndSpecifications from '../dndSpecifications';

class DraggableCircle extends React.Component {

    constructor(props) {
        super(props);

        let borderStyle = props.object?.borderStyle;
        if (!borderStyle) {
            borderStyle = BorderStyle.SOLID;
        }

        this.state = {
            borderStyle: borderStyle
        };
    }

    updateborderStyle = (event) => {
        const oldValue = this.state.borderStyle;
        const newValue = event.target.value;

        if (oldValue !== newValue) {
            this.setState({
                borderStyle: newValue
            });
        }
    }

    render() {
        const { connectDragSource, isDragging } = this.props;
        const borderStyle = this.state.borderStyle;
        const style = {
            borderStyle: borderStyle,
            opacity: isDragging && this.props.id ? 0 : 1
        };

        return connectDragSource(
            <div id={this.props.id} className="circle element" style={style}>
                <select onChange={this.updateborderStyle} value={this.state.borderStyle}>
                    <option value={BorderStyle.DOTTED}>DOTTED</option>
                    <option value={BorderStyle.SOLID}>SOLID</option>
                    <option value={BorderStyle.DASHED}>DASHED</option>
                </select>
            </div>
        );
    }
}

class Circle extends React.Component {

    render() {
        const { connectDropTarget } = this.props;
        return connectDropTarget(
            <div>
                <DraggableCircle {...this.props} />
            </div>
        );
    }

}

DraggableCircle = DragSource(ElementType.CIRCLE, DndSpecifications.dragSpecification, DndSpecifications.collectSource)(DraggableCircle);
Circle = DropTarget(ElementType.ALL, DndSpecifications.dropSpecification, DndSpecifications.collectTarget)(Circle);

export { DraggableCircle, Circle };
