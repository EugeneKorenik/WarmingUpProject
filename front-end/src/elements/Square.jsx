import React from 'react';
import { DragSource, DropTarget } from "react-dnd";
import { ElementType } from '../model';
import * as DndSpecifications from '../dndSpecifications';

class DraggableSquare extends React.Component {

    constructor(props) {
        super(props);

        let symbol = props.symbol;
        if (!symbol) {
            symbol = "A";
        }

        this.state = {
            symbol: symbol
        };
    }

    processSymbolInput = (event) => {
        const oldValue = this.state.symbol;
        const newValue = event.target.value;
        if (oldValue !== newValue) {
            this.setState({
                symbol: newValue
            });
        }
    }

    render() {
        const { connectDragSource, isDragging } = this.props;
        const style = {
            opacity: isDragging && this.props.id ? 0 : 1
        };

        return connectDragSource(
            <div id={this.props.id} className="square element" style={style}>
                <input type="text" pattern="A" onChange={this.processSymbolInput} required />
                <p>{this.state.symbol}</p>
            </div>
        );
    }
}

class Square extends React.Component {

    render() {
        const { connectDropTarget } = this.props;
        return connectDropTarget(
            <div>
                <DraggableSquare {...this.props} />
            </div>
        );
    }

}

DraggableSquare = DragSource(ElementType.SQUARE, DndSpecifications.dragSpecification, DndSpecifications.collectSource)(DraggableSquare);
Square = DropTarget(ElementType.ALL, DndSpecifications.dropSpecification, DndSpecifications.collectTarget)(Square);

export { DraggableSquare, Square };