import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ObjArte from './obj-arte';
import Artista from './artista';
import Exhibicion from './exhibicion';
import Pintura from './pintura';
import Escultura from './escultura';
import OtroObj from './otro-obj';
import CollecPresta from './collec-presta';
import CollecPerma from './collec-perma';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}obj-arte`} component={ObjArte} />
      <ErrorBoundaryRoute path={`${match.url}artista`} component={Artista} />
      <ErrorBoundaryRoute path={`${match.url}exhibicion`} component={Exhibicion} />
      <ErrorBoundaryRoute path={`${match.url}pintura`} component={Pintura} />
      <ErrorBoundaryRoute path={`${match.url}escultura`} component={Escultura} />
      <ErrorBoundaryRoute path={`${match.url}otro-obj`} component={OtroObj} />
      <ErrorBoundaryRoute path={`${match.url}collec-presta`} component={CollecPresta} />
      <ErrorBoundaryRoute path={`${match.url}collec-perma`} component={CollecPerma} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
