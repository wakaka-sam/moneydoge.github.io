var app = getApp();

Page({
  data: {
    selctShow: false,//控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: ['薪酬筛选（从高到低）', '薪酬筛选（从低到高）'],//下拉列表的数据
    index: 0//选择的下拉列表下标
  },
  // 点击下拉显示框
  selectTap() {
    this.setData({
      selectShow: !this.data.selectShow
    });
  },
  // 点击下拉列表
  optionTap(e) {
    let Index = e.currentTarget.dataset.index;//获取点击的下拉列表的下标
    this.setData({
      index: Index,
      selectShow: !this.data.selectShow
    });
  },
  onLoad: function () {
    this.setData({
      tradeList: [
        { goodsId: "9876", name: '书', price: 1, deliveryp: '天桥下', deliveryn: 1, deliveryt: "04.06.13:00" },
        { goodsId: "9876", name: '衣服', price: 2, deliveryp: '天桥下', deliveryn: 1, deliveryt: "04.06.13:00" }
      ]
    });
    var tradeList = this.data.tradeList
    console.log(tradeList[tradeList.length - 1]['price']);
  }
})