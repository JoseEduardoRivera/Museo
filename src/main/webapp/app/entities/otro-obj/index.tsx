import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OtroObj from './otro-obj';
import OtroObjDetail from './otro-obj-detail';
import OtroObjUpdate from './otro-obj-update';
import OtroObjDeleteDialog from './otro-obj-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OtroObjUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OtroObjUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OtroObjDetail} />
      <ErrorBoundaryRoute path={match.url} component={OtroObj} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OtroObjDeleteDialog} />
  </>
);

export default Routes;
