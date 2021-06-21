import React from 'react';
import { Link } from 'react-router-dom';
import { DraggableCircle } from '../element/Circle';
import { DraggableSquare } from '../element/Square';
import { DraggableTriangle } from '../element/Triangle';
import { DraggableGroup } from '../element/Group';
import { ElementType } from '../util/model';
import Canvas from '../canvas/Canvas';
import * as BackendApi from '../backend-api';
import './picture.css';

class Picture extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      picture: null,
      loaded: false
    };
  }

  componentDidMount() {
    const pictureId = this.props.match.params.pictureId;
    const url = `${BackendApi.pictures}/${pictureId}`;
    const options = {
      headers: BackendApi.applicationJsonContentType
    }

    fetch(url, options)
      .then(response => response.json())
      .then(pictureData => {
        this.bindWithParents(pictureData.rootGroup);
        this.setState({
          picture: pictureData,
          loaded: true
        });
      });
  }

  bindWithParents = (element) => {
    element.figures.forEach(figure => {
      figure.parent = element;
      if (figure.type === ElementType.GROUP) {
        this.bindWithParents(figure);
      }
    });
  }

  moveElement = (droppableElementId, element) => {
    if (element.id !== droppableElementId || !element.id) {
      let topLevelGroups = this.unbindFromParent(element);
      const insertInformation = this.insertElement(topLevelGroups, droppableElementId, element);

      if (!element.id) {
        this.createElement(element, insertInformation, topLevelGroups);
      } else {
        this.updateElement(element, insertInformation, topLevelGroups);
      }
    }
  }

  updateElement = (element, insertInfo, topLevelGroups) => {
    const url = `${BackendApi.figures}/${element.type.toUpperCase()}/${element.id}/?groupId=${insertInfo.groupId}&index=${insertInfo.index}`;
    const elementWithoutCycleDependencies = {
      ...element,
      parent: null,
      figures: null
    };

    const options = {
      method: 'put',
      headers: BackendApi.applicationJsonContentType,
      body: JSON.stringify(elementWithoutCycleDependencies)
    };

    fetch(url, options)
      .then(response => response.json())
      .then(response => {
        const pictureInfo = this.state.picture;
        pictureInfo.rootGroup.figures = topLevelGroups;
        this.setState({
          picture: pictureInfo
        });
      });
  }

  createElement = (element, insertInfo, topLevelGroups) => {
    const url = `${BackendApi.figures}/?groupId=${insertInfo.groupId}&index=${insertInfo.index}`;
    const elementWithoutCycleDependencies = {
      ...element,
      parent: null
    };

    const options = {
      method: 'post',
      headers: BackendApi.applicationJsonContentType,
      body: JSON.stringify(elementWithoutCycleDependencies)
    };

    fetch(url, options)
      .then(response => response.json())
      .then(response => {
        element.id = response.id;
        const pictureInfo = this.state.picture;
        pictureInfo.rootGroup.figures = topLevelGroups;
        this.setState({
          picture: pictureInfo
        });
      });
  }

  insertElement = (topLevelGroups, droppableElementId, element) => {
    if (!droppableElementId) {
      return this.bindToRootGroup(topLevelGroups, element);
    } else {
      const droppableElement = this.findElementById(topLevelGroups, droppableElementId);
      if (droppableElement) {
        if (droppableElement.type === ElementType.GROUP) {
          return this.appendToGroup(droppableElement, element);
        } else {
          return this.moveToElementPosition(droppableElement, element);
        }

      } else if (element.type === ElementType.GROUP) {
        return this.bindToRootGroup(topLevelGroups, element);
      }
    }
  }

  bindToRootGroup = (topLevelGroups, element) => {
    const rootGroup = this.state.picture.rootGroup;
    element.parent = rootGroup;
    topLevelGroups.push(element);
    return {
      groupId: rootGroup.id,
      index: topLevelGroups.length - 1
    };
  }

  appendToGroup = (droppableElement, element) => {
    if (!droppableElement.figures) {
      droppableElement.figures = [];
    }

    droppableElement.figures.push(element);
    element.parent = droppableElement;
    element.index = droppableElement.figures.length - 1;

    return {
      groupId: element.parent.id,
      index: element.index
    };
  }

  moveToElementPosition = (droppableElement, element) => {
    const newParentGroup = droppableElement.parent;
    const oldIndex = element.index;
    let indexToInsert = newParentGroup.figures.indexOf(droppableElement);

    if (element.parent === newParentGroup && oldIndex <= indexToInsert) {
      indexToInsert++;
    }

    if (indexToInsert === newParentGroup.figures.length && oldIndex <= indexToInsert) {
      newParentGroup.figures.push(element);
      element.index = newParentGroup.figures.length - 1;
    } else {
      newParentGroup.figures.splice(indexToInsert, 0, element);
      element.index = indexToInsert;
    }

    element.parent = newParentGroup;

    return {
      groupId: element.parent.id,
      index: element.index
    }
  }

  unbindFromParent = (element) => {
    const parent = element.parent;
    if (parent) {
      parent.figures = parent.figures.filter(nextChild => nextChild.id !== element.id);
    }

    const groups = this.state.picture.rootGroup.figures;
    return groups.filter(nextElement => nextElement.id !== element.id);
  }

  removeElement = (element) => {
    const parent = element.parent;
    const indexOfElement = parent.figures.indexOf(element);
    parent.figures.splice(indexOfElement, 1);
  
    const picture = this.state.picture;
    picture.rootGroup.figures = [...picture.rootGroup.figures];
    this.setState({
      picture: picture
    });
  }

  findElementById = (figures, identifier) => {
    if (!figures) {
      return null;
    }

    for (const nextElement of figures) {
      if (nextElement.id === identifier) {
        return nextElement;
      }

      const foundElement = this.findElementById(nextElement.figures, identifier);
      if (foundElement !== null) {
        return foundElement;
      }

    }

    return null;
  }

  provideMainView = () => {
    const figures = this.state.picture.rootGroup.figures;
    return (
      <div className="constructor-frame">
        <div className="sideBar">
          <Link to="/">
            <button>To main page</button>
          </Link>
          <div className="library">
            <DraggableCircle />
            <DraggableSquare />
            <DraggableTriangle />
            <DraggableGroup />
          </div>
        </div>
        <Canvas figures={figures} moveElement={this.moveElement} removeElement={this.removeElement}/>
      </div>
    );
  }

  render() {
    const loadingView = <div></div>
    return this.state.loaded ? this.provideMainView() : loadingView;
  }

}

export default Picture;
