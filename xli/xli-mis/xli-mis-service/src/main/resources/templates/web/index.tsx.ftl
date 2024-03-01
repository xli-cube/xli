import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer, message, Modal} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {FooterToolbar, PageContainer} from '@ant-design/pro-layout';
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type {ProDescriptionsItemProps} from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import UpdateForm from './components/UpdateForm';
import {add, remove, search, update} from './service';
import type {${entityDTO}} from './data';
import CreateForm from "./components/CreateForm";
import type {Pagination} from "@/services/data";


const ${componentName}: React.FC<{ selectedKey: string }> = ({selectedKey}) => {
const [createModalVisible, handleModalVisible] = useState
<boolean>(false);
    const [updateModalVisible, handleUpdateModalVisible] = useState
    <boolean>(false);
        const [showDetail, setShowDetail] = useState
        <boolean>(false);
            const actionRef = useRef
            <ActionType>();
                const [currentRow, setCurrentRow] = useState<${entityDTO}>();
                const [selectedRowsState, setSelectedRows] = useState<${entityDTO}[]>([]);
                const [currentPage, setCurrentPage] = useState
                <number>(1);

                    const columns: ProColumns<${entityDTO}>[] = [
                    {
                    title: '序', dataIndex: 'index', valueType: 'index', width: '60px',
                    render: (_, __, index) => (currentPage - 1) * 10 + index + 1,
                    },
                    <#list tableStructs as tableStruct>
                        <#if tableStruct.showInTable>
                            {
                            title: '${tableStruct.fieldNameCn}', dataIndex: '${tableStruct.fieldDTO}'
                            },
                        </#if>
                    </#list>
                    {
                    title: '操作', valueType: 'option', width: '120px',
                    render: (_, entity) => [
                    <a key="edit"
                       onClick={
                       ()=> {
                        setCurrentRow(entity);
                        handleUpdateModalVisible(true);
                        }
                        }>编辑
                    </a>,
                    <a key="detail"
                       onClick={
                       ()=> {
                        setCurrentRow(entity);
                        setShowDetail(true);
                        }
                        }>查看
                    </a>,
                    <a key="delete" style={{color: 'red'}} onClick={
                    () => {
                    Modal.confirm({
                    title: '确认删除',
                    content: '您确定要删除吗？',
                    okText: '确认',
                    cancelText: '取消',
                    onOk: async () => {
                    const resultVO = await remove([entity]);
                    if (resultVO.code === '1') {
                    message.success(resultVO.msg);
                    actionRef.current?.reloadAndRest?.();
                    } else {
                    message.error(resultVO.msg);
                    }
                    },
                    onCancel: () => {
                    // 用户点击取消按钮时触发的回调
                    }
                    })
                    }
                    }
                    >
                    删除
                    </a>
                    ]
                    }
                    ];

                    useEffect(() => {
                    if (selectedKey) {
                    actionRef.current?.reload();
                    }
                    }, [selectedKey]);

                    return (
                    <PageContainer title={false} breadcrumb={{routes: []}}>
                        <ProTable
                        <${entityDTO}, Pagination>
                        headerTitle="列表"
                        actionRef={actionRef}
                        rowKey="id"
                        request={
                        async (params) => {
                        const resultVO = await search({...params});
                        if (resultVO.code === '1') {
                        return resultVO.data;
                        } else {
                        message.error(resultVO.msg);
                        }
                        }
                        }
                        columns={columns}
                        onChange={(pagination) => setCurrentPage(pagination.current || 1)}
                        search={{
                        labelWidth: 'auto',
                        }}
                        pagination={{
                        defaultPageSize: 10,
                        showSizeChanger: true,
                        pageSizeOptions: ['10', '20', '50', '100', '1000'],
                        }}
                        toolBarRender={
                        () => [
                        <Button
                                type="primary"
                                key="id"
                                onClick={
                                ()=> {
                            handleModalVisible(true);
                            }
                            }
                            >
                            <PlusOutlined/>
                            新建
                        </Button>
                        ]
                        }
                        rowSelection={
                        {
                        onChange: (_, selectedRows) => {
                        setSelectedRows(selectedRows);
                        }
                        }
                        }
                        />
                        {selectedRowsState?.length > 0 && (
                        <FooterToolbar
                                extra={<div> 已选择{' '}<a style={{fontWeight: 600}}>{selectedRowsState.length}</a>{'
                            '}项</div>}>
                            <Button
                                    danger
                                    onClick={
                                    async ()=> {
                                const resultVO = await remove(selectedRowsState);
                                if (resultVO.code === '1') {
                                message.success(resultVO.msg);
                                setSelectedRows([]);
                                actionRef.current?.reloadAndRest?.();
                                } else {
                                message.error(resultVO.msg);
                                }
                                }}>批量删除
                            </Button>
                        </FooterToolbar>
                        )}
                        <CreateForm
                                modalVisible={createModalVisible}
                                onCancel={
                                ()=> {
                            handleModalVisible(false);
                            }
                            }
                            onSubmit={
                            async (value) => {
                            const resultVO = await add({...value, ouId: selectedKey});
                            if (resultVO.code === '1') {
                            handleModalVisible(false);
                            message.success(resultVO.msg);
                            if (actionRef.current) {
                            actionRef.current.reload();
                            }
                            } else {
                            message.error(resultVO.msg);
                            }
                            return resultVO.code === '1';
                            }
                            } />
                            <UpdateForm
                                    onSubmit={
                                    async (value)=> {
                                const resultVO = await update(value);
                                if (resultVO.code === '1') {
                                handleUpdateModalVisible(false);
                                setCurrentRow(undefined);
                                if (actionRef.current) {
                                actionRef.current.reload();
                                }
                                } else {
                                message.error(resultVO.msg);
                                }
                                }
                                }
                                onCancel={
                                () => {
                                handleUpdateModalVisible(false);
                                if (!showDetail) {
                                setCurrentRow(undefined);
                                }
                                }
                                }
                                updateModalVisible={updateModalVisible}
                                values={currentRow || {}}
                                />
                                <Drawer
                                        width={600}
                                        open={showDetail}
                                        onClose={()
                                => {
                                setCurrentRow(undefined);
                                setShowDetail(false);
                                }}
                                closable={false}
                                >
                                {currentRow?.id && (
                                <ProDescriptions
                                <${entityDTO}>
                                column={2}
                                title={currentRow?.displayName}
                                request={
                                async () => (
                                {
                                data: currentRow || {},
                                }
                                )
                                }
                                params={
                                {
                                id: currentRow?.id,
                                }
                                }
                                columns={columns as ProDescriptionsItemProps<${entityDTO}>[]}
                                />)
                                }
                                </Drawer>
                    </PageContainer>
                    );
                    };

                    export default ${componentName};
