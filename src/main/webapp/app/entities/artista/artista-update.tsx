import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IObjArte } from 'app/shared/model/obj-arte.model';
import { getEntities as getObjArtes } from 'app/entities/obj-arte/obj-arte.reducer';
import { getEntity, updateEntity, createEntity, reset } from './artista.reducer';
import { IArtista } from 'app/shared/model/artista.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArtistaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArtistaUpdate = (props: IArtistaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { artistaEntity, objArtes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/artista' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getObjArtes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...artistaEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="museoApp.artista.home.createOrEditLabel" data-cy="ArtistaCreateUpdateHeading">
            <Translate contentKey="museoApp.artista.home.createOrEditLabel">Create or edit a Artista</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : artistaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="artista-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="artista-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomArtLabel" for="artista-nomArt">
                  <Translate contentKey="museoApp.artista.nomArt">Nom Art</Translate>
                </Label>
                <AvField
                  id="artista-nomArt"
                  data-cy="nomArt"
                  type="text"
                  name="nomArt"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechNacLabel" for="artista-fechNac">
                  <Translate contentKey="museoApp.artista.fechNac">Fech Nac</Translate>
                </Label>
                <AvField
                  id="artista-fechNac"
                  data-cy="fechNac"
                  type="date"
                  name="fechNac"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechDefuLabel" for="artista-fechDefu">
                  <Translate contentKey="museoApp.artista.fechDefu">Fech Defu</Translate>
                </Label>
                <AvField id="artista-fechDefu" data-cy="fechDefu" type="date" name="fechDefu" />
              </AvGroup>
              <AvGroup>
                <Label id="paisOrigenLabel" for="artista-paisOrigen">
                  <Translate contentKey="museoApp.artista.paisOrigen">Pais Origen</Translate>
                </Label>
                <AvField
                  id="artista-paisOrigen"
                  data-cy="paisOrigen"
                  type="text"
                  name="paisOrigen"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estiloArtLabel" for="artista-estiloArt">
                  <Translate contentKey="museoApp.artista.estiloArt">Estilo Art</Translate>
                </Label>
                <AvField
                  id="artista-estiloArt"
                  data-cy="estiloArt"
                  type="text"
                  name="estiloArt"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="epocaLabel" for="artista-epoca">
                  <Translate contentKey="museoApp.artista.epoca">Epoca</Translate>
                </Label>
                <AvField
                  id="artista-epoca"
                  data-cy="epoca"
                  type="text"
                  name="epoca"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/artista" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  objArtes: storeState.objArte.entities,
  artistaEntity: storeState.artista.entity,
  loading: storeState.artista.loading,
  updating: storeState.artista.updating,
  updateSuccess: storeState.artista.updateSuccess,
});

const mapDispatchToProps = {
  getObjArtes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArtistaUpdate);
