import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Escultura from './escultura';
import EsculturaDetail from './escultura-detail';
import EsculturaUpdate from './escultura-update';
import EsculturaDeleteDialog from './escultura-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EsculturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EsculturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EsculturaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Escultura} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EsculturaDeleteDialog} />
  </>
);

export default Routes;
