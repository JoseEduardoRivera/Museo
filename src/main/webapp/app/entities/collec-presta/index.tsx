import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CollecPresta from './collec-presta';
import CollecPrestaDetail from './collec-presta-detail';
import CollecPrestaUpdate from './collec-presta-update';
import CollecPrestaDeleteDialog from './collec-presta-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CollecPrestaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CollecPrestaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CollecPrestaDetail} />
      <ErrorBoundaryRoute path={match.url} component={CollecPresta} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CollecPrestaDeleteDialog} />
  </>
);

export default Routes;
