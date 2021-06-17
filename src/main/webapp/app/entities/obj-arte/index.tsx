import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ObjArte from './obj-arte';
import ObjArteDetail from './obj-arte-detail';
import ObjArteUpdate from './obj-arte-update';
import ObjArteDeleteDialog from './obj-arte-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ObjArteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ObjArteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ObjArteDetail} />
      <ErrorBoundaryRoute path={match.url} component={ObjArte} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ObjArteDeleteDialog} />
  </>
);

export default Routes;
