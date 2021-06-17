import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './exhibicion.reducer';
import { IExhibicion } from 'app/shared/model/exhibicion.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExhibicionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExhibicionUpdate = (props: IExhibicionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { exhibicionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/exhibicion' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...exhibicionEntity,
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
          <h2 id="museoApp.exhibicion.home.createOrEditLabel" data-cy="ExhibicionCreateUpdateHeading">
            <Translate contentKey="museoApp.exhibicion.home.createOrEditLabel">Create or edit a Exhibicion</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : exhibicionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="exhibicion-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="exhibicion-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomExiLabel" for="exhibicion-nomExi">
                  <Translate contentKey="museoApp.exhibicion.nomExi">Nom Exi</Translate>
                </Label>
                <AvField
                  id="exhibicion-nomExi"
                  data-cy="nomExi"
                  type="text"
                  name="nomExi"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechIniLabel" for="exhibicion-fechIni">
                  <Translate contentKey="museoApp.exhibicion.fechIni">Fech Ini</Translate>
                </Label>
                <AvField
                  id="exhibicion-fechIni"
                  data-cy="fechIni"
                  type="date"
                  name="fechIni"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechFinLabel" for="exhibicion-fechFin">
                  <Translate contentKey="museoApp.exhibicion.fechFin">Fech Fin</Translate>
                </Label>
                <AvField
                  id="exhibicion-fechFin"
                  data-cy="fechFin"
                  type="date"
                  name="fechFin"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/exhibicion" replace color="info">
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
  exhibicionEntity: storeState.exhibicion.entity,
  loading: storeState.exhibicion.loading,
  updating: storeState.exhibicion.updating,
  updateSuccess: storeState.exhibicion.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExhibicionUpdate);
