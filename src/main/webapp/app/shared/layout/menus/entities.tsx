import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/obj-arte">
      <Translate contentKey="global.menu.entities.objArte" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/artista">
      <Translate contentKey="global.menu.entities.artista" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/exhibicion">
      <Translate contentKey="global.menu.entities.exhibicion" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pintura">
      <Translate contentKey="global.menu.entities.pintura" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/escultura">
      <Translate contentKey="global.menu.entities.escultura" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/otro-obj">
      <Translate contentKey="global.menu.entities.otroObj" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/collec-presta">
      <Translate contentKey="global.menu.entities.collecPresta" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/collec-perma">
      <Translate contentKey="global.menu.entities.collecPerma" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
