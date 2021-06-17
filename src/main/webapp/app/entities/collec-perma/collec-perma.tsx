import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './collec-perma.reducer';
import { ICollecPerma } from 'app/shared/model/collec-perma.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICollecPermaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CollecPerma = (props: ICollecPermaProps) => {
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

  const { collecPermaList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="collec-perma-heading" data-cy="CollecPermaHeading">
        <Translate contentKey="museoApp.collecPerma.home.title">Collec Permas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="museoApp.collecPerma.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="museoApp.collecPerma.home.createLabel">Create new Collec Perma</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {collecPermaList && collecPermaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="museoApp.collecPerma.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="museoApp.collecPerma.objArte">Obj Arte</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('exhibiAlmacen')}>
                  <Translate contentKey="museoApp.collecPerma.exhibiAlmacen">Exhibi Almacen</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('costo')}>
                  <Translate contentKey="museoApp.collecPerma.costo">Costo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fecha')}>
                  <Translate contentKey="museoApp.collecPerma.fecha">Fecha</Translate> <FontAwesomeIcon icon="sort" />
                </th>

                <th />
              </tr>
            </thead>
            <tbody>
              {collecPermaList.map((collecPerma, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${collecPerma.id}`} color="link" size="sm">
                      {collecPerma.id}
                    </Button>
                  </td>
                  <td>
                    {collecPerma.objArte ? <Link to={`obj-arte/${collecPerma.objArte.id}`}>{collecPerma.objArte.idObjArt}</Link> : ''}
                  </td>
                  <td>{collecPerma.exhibiAlmacen}</td>
                  <td>{collecPerma.costo}</td>
                  <td>{collecPerma.fecha}</td>

                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${collecPerma.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${collecPerma.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${collecPerma.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="museoApp.collecPerma.home.notFound">No Collec Permas found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={collecPermaList && collecPermaList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ collecPerma }: IRootState) => ({
  collecPermaList: collecPerma.entities,
  loading: collecPerma.loading,
  totalItems: collecPerma.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CollecPerma);
