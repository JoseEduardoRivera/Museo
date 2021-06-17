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
import { getEntity, updateEntity, createEntity, reset } from './collec-perma.reducer';
import { ICollecPerma } from 'app/shared/model/collec-perma.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICollecPermaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CollecPermaUpdate = (props: ICollecPermaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { collecPermaEntity, objArtes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/collec-perma' + props.location.search);
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
        ...collecPermaEntity,
        ...values,
        objArte: objArtes.find(it => it.id.toString() === values.objArteId.toString()),
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
          <h2 id="museoApp.collecPerma.home.createOrEditLabel" data-cy="CollecPermaCreateUpdateHeading">
            <Translate contentKey="museoApp.collecPerma.home.createOrEditLabel">Create or edit a CollecPerma</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : collecPermaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="collec-perma-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="collec-perma-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="collec-perma-objArte">
                  <Translate contentKey="museoApp.collecPerma.objArte">Obj Arte</Translate>
                </Label>
                <AvInput id="collec-perma-objArte" data-cy="objArte" type="select" className="form-control" name="objArteId">
                  <option value="" key="0" />
                  {objArtes
                    ? objArtes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.idObjArt}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="exhibiAlmacenLabel" for="collec-perma-exhibiAlmacen">
                  <Translate contentKey="museoApp.collecPerma.exhibiAlmacen">Exhibi Almacen</Translate>
                </Label>
                <AvField
                  id="collec-perma-exhibiAlmacen"
                  data-cy="exhibiAlmacen"
                  type="text"
                  name="exhibiAlmacen"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="costoLabel" for="collec-perma-costo">
                  <Translate contentKey="museoApp.collecPerma.costo">Costo</Translate>
                </Label>
                <AvField
                  id="collec-perma-costo"
                  data-cy="costo"
                  type="text"
                  name="costo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaLabel" for="collec-perma-fecha">
                  <Translate contentKey="museoApp.collecPerma.fecha">Fecha</Translate>
                </Label>
                <AvField
                  id="collec-perma-fecha"
                  data-cy="fecha"
                  type="date"
                  name="fecha"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/collec-perma" replace color="info">
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
  collecPermaEntity: storeState.collecPerma.entity,
  loading: storeState.collecPerma.loading,
  updating: storeState.collecPerma.updating,
  updateSuccess: storeState.collecPerma.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(CollecPermaUpdate);
