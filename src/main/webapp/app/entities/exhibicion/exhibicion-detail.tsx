import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './exhibicion.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExhibicionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExhibicionDetail = (props: IExhibicionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { exhibicionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="exhibicionDetailsHeading">
          <Translate contentKey="museoApp.exhibicion.detail.title">Exhibicion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{exhibicionEntity.id}</dd>
          <dt>
            <span id="nomExi">
              <Translate contentKey="museoApp.exhibicion.nomExi">Nom Exi</Translate>
            </span>
          </dt>
          <dd>{exhibicionEntity.nomExi}</dd>
          <dt>
            <span id="fechIni">
              <Translate contentKey="museoApp.exhibicion.fechIni">Fech Ini</Translate>
            </span>
          </dt>
          <dd>{exhibicionEntity.fechIni}</dd>
          <dt>
            <span id="fechFin">
              <Translate contentKey="museoApp.exhibicion.fechFin">Fech Fin</Translate>
            </span>
          </dt>
          <dd>{exhibicionEntity.fechFin}</dd>
        </dl>
        <Button tag={Link} to="/exhibicion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exhibicion/${exhibicionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ exhibicion }: IRootState) => ({
  exhibicionEntity: exhibicion.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExhibicionDetail);
