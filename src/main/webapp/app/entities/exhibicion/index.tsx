import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Exhibicion from './exhibicion';
import ExhibicionDetail from './exhibicion-detail';
import ExhibicionUpdate from './exhibicion-update';
import ExhibicionDeleteDialog from './exhibicion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExhibicionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExhibicionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExhibicionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Exhibicion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExhibicionDeleteDialog} />
  </>
);

export default Routes;
