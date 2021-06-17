import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Artista from './artista';
import ArtistaDetail from './artista-detail';
import ArtistaUpdate from './artista-update';
import ArtistaDeleteDialog from './artista-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArtistaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArtistaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArtistaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Artista} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArtistaDeleteDialog} />
  </>
);

export default Routes;
