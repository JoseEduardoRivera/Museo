import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CollecPerma from './collec-perma';
import CollecPermaDetail from './collec-perma-detail';
import CollecPermaUpdate from './collec-perma-update';
import CollecPermaDeleteDialog from './collec-perma-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CollecPermaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CollecPermaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CollecPermaDetail} />
      <ErrorBoundaryRoute path={match.url} component={CollecPerma} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CollecPermaDeleteDialog} />
  </>
);

export default Routes;
