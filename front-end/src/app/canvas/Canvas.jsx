import React from 'react';
import { DropTarget } from "react-dnd";
import { ElementType } from "../util/model";
import { dropSpecification, collectTarget } from "../util/dndSpecifications";
import { Group } from '../element/Group';

class Canvas extends React.Component {

    renderFigures(topLevelGroups) {
        return topLevelGroups.map(nextGroup => {
            const children = nextGroup.figures ? [...nextGroup.figures] : [];
            return <Group id={nextGroup.id}
                key={nextGroup.id}
                object={nextGroup}
                children={children}
                moveElement={this.props.moveElement}
                removeElement={this.props.removeElement}
            />
        });
    }

    render() {
        const renderedFigures = this.renderFigures(this.props.figures);
        const { connectDropTarget } = this.props;
        return connectDropTarget(
            <div className="canvas">
                {renderedFigures}
            </div>
        );
    }
}

export default DropTarget(ElementType.GROUP, dropSpecification, collectTarget)(Canvas);