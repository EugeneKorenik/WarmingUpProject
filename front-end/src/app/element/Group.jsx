import React from 'react';
import { Square } from './Square';
import { Circle } from './Circle';
import { Triangle } from './Triangle';
import { DropTarget, DragSource } from "react-dnd";
import { ElementType, GroupType } from "../util/model";
import * as DndSpecifications from '../util/dndSpecifications';
import * as BackendApi from '../backend-api';

class DraggableGroup extends React.Component {

    constructor(props) {
        super(props);

        let groupType = props.object?.groupType;
        if (!groupType) {
            groupType = GroupType.VERTICAL;
        }

        this.state = {
            groupType: groupType
        };
    }

    updateGroupType = (event) => {
        const newGroupType = event.target.value;
        const oldGroupType = this.state.type;
        if (newGroupType !== oldGroupType) {
            this.setState({
                groupType: newGroupType
            });

            BackendApi.updateFigure(
                this.props.object.id,
                ElementType.GROUP,
                {
                    'groupType': newGroupType
                }
            )
        }
    }

    renderChildren(children) {
        if (!children) {
            return;
        }

        return children.map(nextElement => {
            const elementIndex = children.indexOf(nextElement);

            if (nextElement.type === ElementType.GROUP) {
                const children = nextElement.figures ? [...nextElement.figures] : [];
                return <Group index={elementIndex}
                    object={nextElement}
                    children={children}
                    moveElement={this.props.moveElement}
                />
            }

            switch (nextElement.type) {
                case ElementType.SQUARE:
                    return <Square
                        index={elementIndex}
                        object={nextElement}
                        moveElement={this.props.moveElement}
                    />;
                case ElementType.TRIANGLE:
                    return <Triangle
                        index={elementIndex}
                        object={nextElement}
                        moveElement={this.props.moveElement}
                    />;
                case ElementType.CIRCLE:
                    return <Circle
                        index={elementIndex}
                        object={nextElement}
                        moveElement={this.props.moveElement}
                    />;
                default:
                    throw new Error(`Can't use next type: ${nextElement.type}`);
            }
        });
    }

    render() {
        const renderedChildren = this.renderChildren(this.props?.object?.figures);
        const { connectDragSource } = this.props;
        const groupStyle = {
            flexDirection: this.state.groupType
        };

        return connectDragSource(
            <div className="group element" style={groupStyle}>
                <select value={this.state.groupType} onChange={this.updateGroupType}>
                    <option value={GroupType.VERTICAL}>VERTICAL</option>
                    <option value={GroupType.HORIZONTAL}>HORIZONTAL</option>
                </select>
                {renderedChildren}
            </div>
        );
    }
}

class Group extends React.Component {
    render() {
        const { connectDropTarget, object } = this.props;
        return connectDropTarget(
            <div id={object.id} key={object.id}>
                <DraggableGroup {...this.props} />
            </div>
        );
    }
}

DraggableGroup = DragSource(ElementType.GROUP, DndSpecifications.dragSpecification, DndSpecifications.collectSource)(DraggableGroup);
Group = DropTarget(ElementType.ALL, DndSpecifications.dropSpecification, DndSpecifications.collectTarget)(Group);

export { DraggableGroup, Group };