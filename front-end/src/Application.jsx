import React from 'react';
import { DraggableCircle } from './elements/Circle';
import { DraggableSquare } from './elements/Square';
import { DraggableTriangle } from './elements/Triangle';
import { DraggableGroup } from './elements/Group';
import { ElementType } from './model';
import Canvas from './elements/Canvas';
import './app.css';

class Application extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      elements: []
    }
  }

  moveElement = (droppableElementId, element) => {
    if (element.id !== droppableElementId) {
      let rootGroups = this.unbindFromParent(element);
      this.insertElement(rootGroups, droppableElementId, element);
      this.setState({
        elements: rootGroups
      });
    }
  }

  insertElement = (rootGroups, droppableElementId, element) => { 
    if (!droppableElementId) {
      rootGroups.push(element);
      element.parent = null;
    } else {
      const droppableElement = this.findElementById(rootGroups, droppableElementId);
      if (droppableElement) {
        if (droppableElement.type === ElementType.GROUP) {
          this.appendToGroup(droppableElement, element);
        } else {
          this.moveToElementPosition(droppableElement, element);
        }
      } else {
        if (element.type === ElementType.GROUP) {
          rootGroups.push(element);
          element.parent = null;
        }
      }
    }
  }

  appendToGroup = (droppableElement, element) => {
    if (!droppableElement.elements) {
      droppableElement.elements = [];
    }

    droppableElement.elements.push(element);
    element.parent = droppableElement;
    element.index = droppableElement.elements.length - 1;
  }

  moveToElementPosition = (droppableElement, element) => {
    const newParentGroup = droppableElement.parent;
    const oldIndex = element.index;
    let indexToInsert = newParentGroup.elements.indexOf(droppableElement);

    if (element.parent === newParentGroup && oldIndex <= indexToInsert) {
      indexToInsert++;
    }

    if (indexToInsert === newParentGroup.elements.length && oldIndex <= indexToInsert) {
      newParentGroup.elements.push(element);
      element.index = newParentGroup.elements.length - 1;
    } else {
      newParentGroup.elements.splice(indexToInsert, 0, element);
      element.index = indexToInsert;
    }

    element.parent = newParentGroup;
  }

  unbindFromParent = (element) => {
    const parent = element.parent;
    if (parent) {
      parent.elements = parent.elements.filter(nextChild => nextChild.id !== element.id);
    }
    return this.state.elements.filter(nextElement => nextElement.id !== element.id);
  }

  findElementById = (elements, identifier) => {
    if (!elements) {
      return null;
    }

    for (const nextElement of elements) {
      if (nextElement.id === identifier) {
        return nextElement;
      }

      const foundElement = this.findElementById(nextElement.elements, identifier);
      if (foundElement !== null) {
        return foundElement;
      }

    }

    return null;
  }

  render() {
    const oldModel = (
      <div className="application">
        <div className="library">
          <DraggableCircle />;
          <DraggableSquare />
          <DraggableTriangle />
          <DraggableGroup />
        </div>
        <Canvas elements={this.state.elements} moveElement={this.moveElement} />
      </div>
    );

    return oldModel;
  }
}

export default Application;
