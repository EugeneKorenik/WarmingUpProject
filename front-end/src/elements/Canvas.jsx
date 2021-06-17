import React from 'react';
import { DropTarget } from "react-dnd";
import { ElementType } from "../model";
import { dropSpecification, collectTarget } from "../dndSpecifications";
import { Group } from './Group';

class Canvas extends React.Component {

    renderFigures() {
        return this.props.elements.map(nextGroup => {
            const children = nextGroup.elements ? [...nextGroup.elements] : [];
            return <Group id={nextGroup.id}
                key={nextGroup.id}
                object={nextGroup}
                children={children}
                moveElement={this.props.moveElement}
            />
        });
    }

    render() {
        const renderedFigures = this.renderFigures(this.props.elements);
        const { connectDropTarget } = this.props;
        return connectDropTarget(
            <div className="canvas">
                {renderedFigures}
            </div>
        );
    }
}

export default DropTarget(ElementType.GROUP, dropSpecification, collectTarget)(Canvas);