import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArtista } from 'app/shared/model/artista.model';
import { getEntities as getArtistas } from 'app/entities/artista/artista.reducer';
import { IExhibicion } from 'app/shared/model/exhibicion.model';
import { getEntities as getExhibicions } from 'app/entities/exhibicion/exhibicion.reducer';
import { getEntity, updateEntity, createEntity, reset } from './obj-arte.reducer';
import { IObjArte } from 'app/shared/model/obj-arte.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IObjArteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ObjArteUpdate = (props: IObjArteUpdateProps) => {
  const [idsartista, setIdsartista] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { objArteEntity, artistas, exhibicions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/obj-arte' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getArtistas();
    props.getExhibicions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...objArteEntity,
        ...values,
        artistas: mapIdList(values.artistas),
        exhibicion: exhibicions.find(it => it.id.toString() === values.exhibicionId.toString()),
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
          <h2 id="museoApp.objArte.home.createOrEditLabel" data-cy="ObjArteCreateUpdateHeading">
            <Translate contentKey="museoApp.objArte.home.createOrEditLabel">Create or edit a ObjArte</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : objArteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="obj-arte-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="obj-arte-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idObjArtLabel" for="obj-arte-idObjArt">
                  <Translate contentKey="museoApp.objArte.idObjArt">Id Obj Art</Translate>
                </Label>
                <AvField
                  id="obj-arte-idObjArt"
                  data-cy="idObjArt"
                  type="text"
                  name="idObjArt"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="obj-arte-exhibicion">
                  <Translate contentKey="museoApp.objArte.exhibicion">Exhibicion</Translate>
                </Label>
                <AvInput id="obj-arte-exhibicion" data-cy="exhibicion" type="select" className="form-control" name="exhibicionId">
                  <option value="" key="0" />
                  {exhibicions
                    ? exhibicions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nomExi}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="paisCulturaLabel" for="obj-arte-paisCultura">
                  <Translate contentKey="museoApp.objArte.paisCultura">Pais Cultura</Translate>
                </Label>
                <AvField
                  id="obj-arte-paisCultura"
                  data-cy="paisCultura"
                  type="text"
                  name="paisCultura"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="anioLabel" for="obj-arte-anio">
                  <Translate contentKey="museoApp.objArte.anio">Anio</Translate>
                </Label>
                <AvField id="obj-arte-anio" data-cy="anio" type="date" name="anio" />
              </AvGroup>
              <AvGroup>
                <Label id="tituloObjLabel" for="obj-arte-tituloObj">
                  <Translate contentKey="museoApp.objArte.tituloObj">Titulo Obj</Translate>
                </Label>
                <AvField
                  id="obj-arte-tituloObj"
                  data-cy="tituloObj"
                  type="text"
                  name="tituloObj"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descObjLabel" for="obj-arte-descObj">
                  <Translate contentKey="museoApp.objArte.descObj">Desc Obj</Translate>
                </Label>
                <AvField
                  id="obj-arte-descObj"
                  data-cy="descObj"
                  type="text"
                  name="descObj"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="epocaObjLabel" for="obj-arte-epocaObj">
                  <Translate contentKey="museoApp.objArte.epocaObj">Epoca Obj</Translate>
                </Label>
                <AvField
                  id="obj-arte-epocaObj"
                  data-cy="epocaObj"
                  type="text"
                  name="epocaObj"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="obj-arte-artista">
                  <Translate contentKey="museoApp.objArte.artista">Artista</Translate>
                </Label>
                <AvInput
                  id="obj-arte-artista"
                  data-cy="artista"
                  type="select"
                  multiple
                  className="form-control"
                  name="artistas"
                  value={!isNew && objArteEntity.artistas && objArteEntity.artistas.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {artistas
                    ? artistas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nomArt}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/obj-arte" replace color="info">
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
  artistas: storeState.artista.entities,
  exhibicions: storeState.exhibicion.entities,
  objArteEntity: storeState.objArte.entity,
  loading: storeState.objArte.loading,
  updating: storeState.objArte.updating,
  updateSuccess: storeState.objArte.updateSuccess,
});

const mapDispatchToProps = {
  getArtistas,
  getExhibicions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ObjArteUpdate);
