import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './collec-presta.reducer';
import { ICollecPresta } from 'app/shared/model/collec-presta.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICollecPrestaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CollecPresta = (props: ICollecPrestaProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { collecPrestaList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="collec-presta-heading" data-cy="CollecPrestaHeading">
        <Translate contentKey="museoApp.collecPresta.home.title">Collec Prestas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="museoApp.collecPresta.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="museoApp.collecPresta.home.createLabel">Create new Collec Presta</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {collecPrestaList && collecPrestaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="museoApp.collecPresta.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="museoApp.collecPresta.objArte">Obj Arte</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('infPrest')}>
                  <Translate contentKey="museoApp.collecPresta.infPrest">Inf Prest</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechPrest')}>
                  <Translate contentKey="museoApp.collecPresta.fechPrest">Fech Prest</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechDev')}>
                  <Translate contentKey="museoApp.collecPresta.fechDev">Fech Dev</Translate> <FontAwesomeIcon icon="sort" />
                </th>

                <th />
              </tr>
            </thead>
            <tbody>
              {collecPrestaList.map((collecPresta, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${collecPresta.id}`} color="link" size="sm">
                      {collecPresta.id}
                    </Button>
                  </td>
                  <td>
                    {collecPresta.objArte ? <Link to={`obj-arte/${collecPresta.objArte.id}`}>{collecPresta.objArte.idObjArt}</Link> : ''}
                  </td>
                  <td>{collecPresta.infPrest}</td>
                  <td>{collecPresta.fechPrest}</td>
                  <td>{collecPresta.fechDev}</td>

                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${collecPresta.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${collecPresta.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${collecPresta.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="museoApp.collecPresta.home.notFound">No Collec Prestas found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={collecPrestaList && collecPrestaList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ collecPresta }: IRootState) => ({
  collecPrestaList: collecPresta.entities,
  loading: collecPresta.loading,
  totalItems: collecPresta.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CollecPresta);
