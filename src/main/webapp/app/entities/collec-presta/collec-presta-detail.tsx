import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './collec-presta.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICollecPrestaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CollecPrestaDetail = (props: ICollecPrestaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { collecPrestaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="collecPrestaDetailsHeading">
          <Translate contentKey="museoApp.collecPresta.detail.title">CollecPresta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{collecPrestaEntity.id}</dd>
          <dt>
            <span id="infPrest">
              <Translate contentKey="museoApp.collecPresta.infPrest">Inf Prest</Translate>
            </span>
          </dt>
          <dd>{collecPrestaEntity.infPrest}</dd>
          <dt>
            <span id="fechPrest">
              <Translate contentKey="museoApp.collecPresta.fechPrest">Fech Prest</Translate>
            </span>
          </dt>
          <dd>{collecPrestaEntity.fechPrest}</dd>
          <dt>
            <span id="fechDev">
              <Translate contentKey="museoApp.collecPresta.fechDev">Fech Dev</Translate>
            </span>
          </dt>
          <dd>{collecPrestaEntity.fechDev}</dd>
          <dt>
            <Translate contentKey="museoApp.collecPresta.objArte">Obj Arte</Translate>
          </dt>
          <dd>{collecPrestaEntity.objArte ? collecPrestaEntity.objArte.idObjArt : ''}</dd>
        </dl>
        <Button tag={Link} to="/collec-presta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/collec-presta/${collecPrestaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ collecPresta }: IRootState) => ({
  collecPrestaEntity: collecPresta.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CollecPrestaDetail);
