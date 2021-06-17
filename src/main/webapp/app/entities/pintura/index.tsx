import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pintura from './pintura';
import PinturaDetail from './pintura-detail';
import PinturaUpdate from './pintura-update';
import PinturaDeleteDialog from './pintura-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PinturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PinturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PinturaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pintura} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PinturaDeleteDialog} />
  </>
);

export default Routes;
