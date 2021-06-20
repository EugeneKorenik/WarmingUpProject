import React from 'react';
import { DragSource, DropTarget } from "react-dnd";
import { ElementType } from '../util/model';
import * as DndSpecifications from '../util/dndSpecifications';
import * as BackendApi from '../backend-api';

class DraggableSquare extends React.Component {

    regexp = /^[A-Z0-9]$/;

    constructor(props) {
        super(props);

        let symbol = props.object?.symbol;
        if (!symbol) {
            symbol = "A";
        }

        this.state = {
            symbol: symbol
        };

        this.symbolRef = React.createRef();
    }

    updateSymbol = () => {
        const inputElement = this.symbolRef.current;
        const newValue = inputElement.value;

        if (this.regexp.test(newValue)) {
            this.setState({
                symbol: newValue
            });

            BackendApi.updateFigure(
                this.props.object.id,
                ElementType.SQUARE, 
                {
                    symbol: newValue
                }
            );
        } else {
            inputElement.value = this.state.symbol;
        }
    }

    processClick = (event) => {
        console.log(event.button);
        if (event.button === 1) {
            event.preventDefault();
            this.props.removeElement(this.props.object);
        }
    }

    render() {
        const { connectDragSource, isDragging } = this.props;
        const style = {
            opacity: isDragging && this.props.id ? 0 : 1
        };

        return connectDragSource(
            <div id={this.props.id} className="square element" style={style} onClick={this.processClick}>
                <input type="text"
                    onBlur={this.updateSymbol}
                    ref={this.symbolRef}
                    required />
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