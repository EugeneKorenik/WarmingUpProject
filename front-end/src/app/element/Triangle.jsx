import React from 'react';
import { DragSource, DropTarget } from "react-dnd";
import { ElementType } from '../util/model';
import * as DndSpecifications from '../util/dndSpecifications';
import * as BackendApi from '../backend-api';

class DraggableTriangle extends React.Component {

    constructor(props) {
        super(props);

        let colorHex = props.object?.colorHex;
        if (!colorHex) {
            colorHex = "#c2c2c2";
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

    saveChanges = (event) => {
        BackendApi.updateFigure(
            this.props.object.id,
            ElementType.TRIANGLE,
            {
                colorHex: this.state.colorHex
            }
        );
    }

    render() {
        const { connectDragSource, isDragging } = this.props;
        const style = {
            borderBottomColor: this.state.colorHex,
            opacity: isDragging && this.props.id ? 0 : 1
        };

        return connectDragSource(
            <div className="triangle element" style={style}>
                <input type="color" onChange={this.updateColor} value={this.state.colorHex} onBlur={this.saveChanges} />
            </div>
        );
    }
}

class Triangle extends React.Component {

    removeElement = () => {
        const object = this.props.object;
        BackendApi.deleteFigure(object.id, object.type)
            .then(response => {
                if (response.ok) {
                    this.props.removeElement(object);
                }
            })

    }

    render() {
        const { connectDropTarget, object } = this.props;
        return connectDropTarget(
            <div id={object.id} key={object.id}>
                <button className="delete-button" onClick={this.removeElement}>X</button>
                <DraggableTriangle {...this.props} />
            </div>
        );
    }
}

DraggableTriangle = DragSource(ElementType.TRIANGLE, DndSpecifications.dragSpecification, DndSpecifications.collectSource)(DraggableTriangle);
Triangle = DropTarget(ElementType.ALL, DndSpecifications.dropSpecification, DndSpecifications.collectTarget)(Triangle);

export { DraggableTriangle, Triangle };