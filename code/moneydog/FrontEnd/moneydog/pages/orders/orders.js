//获取应用实例
const app = getApp()

Page({
  data: {
    isSelected1: true,
    isSelected2: false,
    selctShow: false,//控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: ['薪酬筛选（从高到低）', '薪酬筛选（从低到高）'],//下拉列表的数据
    index: 0//选择的下拉列表下标
  },
  select1: function() {
    this.setData({
      isSelected1: true,
      isSelected2: false
    }) 
  },
  select2: function () {
    this.setData({
      isSelected1: false,
      isSelected2: true
    })
  },
  onLoad: function () {
    this.setData({
      tradeList: [
        { goodsId: "9876", name: '书', price: 1, deliveryp: '天桥下', deliveryn: 1, deliveryt: "04.06.13:00" },
        { goodsId: "9876", name: '书', price: 1, deliveryp: '天桥下', deliveryn: 1, deliveryt: "04.06.13:00" }
      ],
      tradeList1: [
        { goodsId: "9876", img_src: '../../images/pulldown.png', name: '蓝牙鼠标', price: 49, info: '九成新华硕蓝牙鼠标便宜卖', deadline: "04.06.13:00" }
      ]
    });
    var tradeList = this.data.tradeList
    console.log(tradeList[tradeList.length - 1]['price']);
  }
})
