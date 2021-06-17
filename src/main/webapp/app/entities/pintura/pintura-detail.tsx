import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pintura.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPinturaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PinturaDetail = (props: IPinturaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pinturaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pinturaDetailsHeading">
          <Translate contentKey="museoApp.pintura.detail.title">Pintura</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pinturaEntity.id}</dd>
          <dt>
            <span id="tipoPintura">
              <Translate contentKey="museoApp.pintura.tipoPintura">Tipo Pintura</Translate>
            </span>
          </dt>
          <dd>{pinturaEntity.tipoPintura}</dd>
          <dt>
            <span id="materialPintura">
              <Translate contentKey="museoApp.pintura.materialPintura">Material Pintura</Translate>
            </span>
          </dt>
          <dd>{pinturaEntity.materialPintura}</dd>
          <dt>
            <span id="estiloPint">
              <Translate contentKey="museoApp.pintura.estiloPint">Estilo Pint</Translate>
            </span>
          </dt>
          <dd>{pinturaEntity.estiloPint}</dd>
          <dt>
            <Translate contentKey="museoApp.pintura.objArte">Obj Arte</Translate>
          </dt>
          <dd>{pinturaEntity.objArte ? pinturaEntity.objArte.idObjArt : ''}</dd>
        </dl>
        <Button tag={Link} to="/pintura" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pintura/${pinturaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pintura }: IRootState) => ({
  pinturaEntity: pintura.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PinturaDetail);
