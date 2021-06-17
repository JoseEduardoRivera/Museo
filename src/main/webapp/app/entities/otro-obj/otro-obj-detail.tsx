import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './otro-obj.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOtroObjDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OtroObjDetail = (props: IOtroObjDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { otroObjEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="otroObjDetailsHeading">
          <Translate contentKey="museoApp.otroObj.detail.title">OtroObj</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{otroObjEntity.id}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="museoApp.otroObj.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{otroObjEntity.tipo}</dd>
          <dt>
            <span id="estilo">
              <Translate contentKey="museoApp.otroObj.estilo">Estilo</Translate>
            </span>
          </dt>
          <dd>{otroObjEntity.estilo}</dd>
          <dt>
            <Translate contentKey="museoApp.otroObj.objArte">Obj Arte</Translate>
          </dt>
          <dd>{otroObjEntity.objArte ? otroObjEntity.objArte.idObjArt : ''}</dd>
        </dl>
        <Button tag={Link} to="/otro-obj" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/otro-obj/${otroObjEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ otroObj }: IRootState) => ({
  otroObjEntity: otroObj.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OtroObjDetail);
