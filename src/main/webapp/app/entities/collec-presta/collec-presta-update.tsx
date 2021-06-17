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
import { getEntity, updateEntity, createEntity, reset } from './collec-presta.reducer';
import { ICollecPresta } from 'app/shared/model/collec-presta.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICollecPrestaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CollecPrestaUpdate = (props: ICollecPrestaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { collecPrestaEntity, objArtes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/collec-presta' + props.location.search);
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
        ...collecPrestaEntity,
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
          <h2 id="museoApp.collecPresta.home.createOrEditLabel" data-cy="CollecPrestaCreateUpdateHeading">
            <Translate contentKey="museoApp.collecPresta.home.createOrEditLabel">Create or edit a CollecPresta</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : collecPrestaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="collec-presta-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="collec-presta-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="collec-presta-objArte">
                  <Translate contentKey="museoApp.collecPresta.objArte">Obj Arte</Translate>
                </Label>
                <AvInput id="collec-presta-objArte" data-cy="objArte" type="select" className="form-control" name="objArteId">
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
                <Label id="infPrestLabel" for="collec-presta-infPrest">
                  <Translate contentKey="museoApp.collecPresta.infPrest">Inf Prest</Translate>
                </Label>
                <AvField
                  id="collec-presta-infPrest"
                  data-cy="infPrest"
                  type="text"
                  name="infPrest"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechPrestLabel" for="collec-presta-fechPrest">
                  <Translate contentKey="museoApp.collecPresta.fechPrest">Fech Prest</Translate>
                </Label>
                <AvField
                  id="collec-presta-fechPrest"
                  data-cy="fechPrest"
                  type="date"
                  name="fechPrest"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechDevLabel" for="collec-presta-fechDev">
                  <Translate contentKey="museoApp.collecPresta.fechDev">Fech Dev</Translate>
                </Label>
                <AvField
                  id="collec-presta-fechDev"
                  data-cy="fechDev"
                  type="date"
                  name="fechDev"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/collec-presta" replace color="info">
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
  collecPrestaEntity: storeState.collecPresta.entity,
  loading: storeState.collecPresta.loading,
  updating: storeState.collecPresta.updating,
  updateSuccess: storeState.collecPresta.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(CollecPrestaUpdate);
