import React, {Component} from "react";
import "./Dashboard.css";

export default class Dashboard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            history: [],
            loaded: false
        }
    }

    componentDidMount() {
        this.setState({
            profitValue: 230.32,
            profitCurrency: "GBP",
            profitPercent: 80,
            profitChange: -1,
            ethPosition: 6.4322,
            ethValue: 345.4,
            ethValueChange: 1,
            loaded: true
        });
    }

    componentWillUpdate(props, state) {
        console.log(state);
        const sign = state.profitChange === -1 ? "-" : "";
        document.title = `${sign}${state.profitValue} ${state.profitCurrency}`;
    }

    static colorForChange(profitChange) {
        if (profitChange === -1) {
            return "red";
        }
        if (profitChange === 1) {
            return "green";
        }
        return "";
    }

    render() {
        const pnlColor = Dashboard.colorForChange(this.state.profitChange);
        const ethColor = Dashboard.colorForChange(this.state.ethValueChange);

        if (!this.state.loaded) {
            return (
                <div className="dashboard">
                    <h1>Loading data...</h1>
                </div>
            )
        }

        return (
            <div className="dashboard">
                <h1>Your {this.state.profitChange === 1 ? "profit" : "loss"} is</h1>
                <span className="profit">
                    <span className={`total-profit ${pnlColor}`}>{this.state.profitValue}</span>
                    <span className="currency">{this.state.profitCurrency}</span>
                </span>

                <span className={`profit-percent ${pnlColor}`}>{this.state.profitPercent}%</span>
                <span className="fx">
                    <span className="position">Ξ {this.state.ethPosition}</span>
                    <span className="separator">@</span>
                    <span className={`value ${ethColor}`}>€{this.state.ethValue}</span>
                </span>

                {/*<span className="history">*/}
                {/*<span className="red">3,454.34</span>*/}
                {/*<span className="green">3,458.20</span>*/}
                {/*<span className="green">3,448.00</span>*/}
                {/*<span className="red">3,440.75</span>*/}
                {/*<span className="green">3,441.90</span>*/}
                {/*</span>*/}
            </div>
        );
    }
}
