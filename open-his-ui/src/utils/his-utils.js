// 项目通用方法的封装
// 可以重置任何页面的表单
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields()
  }
}
// 把日期范围构造成beginTime和endTime
export function addDateRange(params, dateRange) {
  var search = params
  search.beginTime = ''
  search.endTime = ''
  if (dateRange != null && dateRange !== '' && dateRange !== undefined) {
    search.beginTime = this.dateRange[0]
    search.endTime = this.dateRange[1]
  }
  return search
}
// 状态翻译 datas是当前状态数据列表 value要翻译的值
export function selectDictLabel(datas, value) {
  var actions = []
  Object.keys(datas).map((key) => {
    if (datas[key].dictValue === value) {
      actions.push(datas[key].dictLabel)
      return false
    }
  })
  return actions.join('')
}

/** 构造树
 * @Param {*} data 数据源
 * @Param {*} id 字段ID 默认id
 * @Param {*} parentId 父节字段  默认 parentId
 * @Param {*} 子节点字段  默认 children
 * @Param {*} rootId 根节点ID 默认0
*/
export function handleTree(data, id, parentId, children, rootId) {
  id = id || 'id'
  parentId = parentId || 'parentId'
  children = children || 'children'
  rootId = rootId || 0
  // 对源数据深度克隆
  const cloneData = JSON.parse(JSON.stringify(data))
  // 循环所有项目
  const treeData = cloneData.filter(father => {
    const branchArr = cloneData.filter(child => {
      return father[id] === child[parentId]
    })
    // 大于0 说明有子节点
    branchArr.length > 0 ? father.children = branchArr : ''
    // 返回上一层
    return father[parentId] === rootId
  })
  return treeData !== '' ? treeData : data
}
