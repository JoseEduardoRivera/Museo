import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './escultura.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEsculturaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EsculturaDetail = (props: IEsculturaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { esculturaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="esculturaDetailsHeading">
          <Translate contentKey="museoApp.escultura.detail.title">Escultura</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{esculturaEntity.id}</dd>
          <dt>
            <span id="altura">
              <Translate contentKey="museoApp.escultura.altura">Altura</Translate>
            </span>
          </dt>
          <dd>{esculturaEntity.altura}</dd>
          <dt>
            <span id="material">
              <Translate contentKey="museoApp.escultura.material">Material</Translate>
            </span>
          </dt>
          <dd>{esculturaEntity.material}</dd>
          <dt>
            <span id="estilo">
              <Translate contentKey="museoApp.escultura.estilo">Estilo</Translate>
            </span>
          </dt>
          <dd>{esculturaEntity.estilo}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="museoApp.escultura.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{esculturaEntity.peso}</dd>
          <dt>
            <Translate contentKey="museoApp.escultura.objArte">Obj Arte</Translate>
          </dt>
          <dd>{esculturaEntity.objArte ? esculturaEntity.objArte.idObjArt : ''}</dd>
        </dl>
        <Button tag={Link} to="/escultura" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/escultura/${esculturaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ escultura }: IRootState) => ({
  esculturaEntity: escultura.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EsculturaDetail);
